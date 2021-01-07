package com.eles.towerhunter.views.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.location.Location
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.LocalStorage
import com.eles.towerhunter.data.models.PhotoCapture
import com.eles.towerhunter.helpers.SingleLiveEvent
import com.github.pwittchen.reactivesensors.library.ReactiveSensorEvent
import com.github.pwittchen.reactivesensors.library.ReactiveSensors
import com.google.android.gms.location.LocationRequest
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import java8.util.Optional
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import java.io.File
import java.util.*

private const val PERMISSION_CAMERA = Manifest.permission.CAMERA
private const val FOLDER_NAME = "TowerHunter"
private const val FILE_NAME = "photo_%s.jpg"

class CameraViewModel(
    private val storage: LocalStorage = LocalStorage
) : ViewModel() {

    /*
    Exposed LiveData
     */

    private val _cameraPermissionGranted = SingleLiveEvent<Boolean>()
    val cameraPermissionGranted: LiveData<Boolean> get() = _cameraPermissionGranted

    private val _photoCaptured = SingleLiveEvent<PhotoCapture>()
    val photoCaptured: LiveData<PhotoCapture> get() = _photoCaptured

    private val _photoCaptureError = SingleLiveEvent<Throwable>()
    val photoCaptureError: LiveData<Throwable> get() = _photoCaptureError

    /*
    Other vars
     */

    private var outputDirectory: File? = null
    private var cameraPermissionLauncher: ActivityResultLauncher<String>? = null
    private val imageCapture = ImageCapture.Builder().build()
    private var disposable: Disposable? = null

    /*
    Public
     */

    fun prepareActivityResult(fragment: Fragment) {
        cameraPermissionLauncher = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            _cameraPermissionGranted.value = granted
        }
    }

    fun initCameraWithPermissionCheck(fragment: Fragment, cameraSurfaceProvider: Preview.SurfaceProvider) {
        if (checkCameraPermission(fragment.requireContext())) {
            initCamera(fragment, cameraSurfaceProvider)
        } else {
            requestCameraPermission()
        }
    }

    fun initCamera(fragment: Fragment, cameraSurfaceProvider: Preview.SurfaceProvider) {
        val context = fragment.requireContext()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(cameraSurfaceProvider)
            }
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    fragment, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture)

            } catch(exc: Exception) {}

        }, ContextCompat.getMainExecutor(context))
    }

    fun takePhoto(activity: Activity) {
        Observable.zip(
            getPhotoObservable(activity),
            getLocationObservable(activity),
            getMagnetometerSensorObservable(activity),
            Function3<Uri, Optional<Location>, Optional<ReactiveSensorEvent>, PhotoCapture> { photoUri, optionalLocation, optionalMagnetometerSensor ->
                val location = if (optionalLocation.isPresent) optionalLocation.get() else null
                val magnetometerSensor = if (optionalMagnetometerSensor.isPresent) optionalMagnetometerSensor.get() else null
                PhotoCapture(photoUri, location, magnetometerSensor?.sensorValues())
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ photoCapture ->
                savePhotoCapture(photoCapture)
                _photoCaptured.value = photoCapture
            }, { exception ->
                _photoCaptureError.value = exception
            }).let { disposable = it }
    }

    /*
    Private
     */

    private fun preparePhotoFile(activity: Activity): File {
        // Create directory if it doesn't exist yet
        if (outputDirectory == null) {
            val rootDir = activity.externalMediaDirs.firstOrNull() ?: activity.filesDir
            outputDirectory = File(rootDir, FOLDER_NAME).apply { mkdirs() }
        }
        // Prepare File that the photo will save into
        return File(outputDirectory, FILE_NAME.format(Date().time.toString()))
    }

    private fun checkCameraPermission(context: Context) = ContextCompat.checkSelfPermission(
        context,
        PERMISSION_CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun requestCameraPermission() {
        cameraPermissionLauncher?.launch(Manifest.permission.CAMERA)
    }

    private fun savePhotoCapture(photoCapture: PhotoCapture) {
        storage.lastPhotoCapture = photoCapture
    }

    private fun getPhotoObservable(activity: Activity): Observable<Uri> {
        return Observable.create { emitter ->
            val photoFile = preparePhotoFile(activity)
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
            imageCapture.takePicture(
                outputOptions, ContextCompat.getMainExecutor(activity.applicationContext), object : ImageCapture.OnImageSavedCallback {
                    override fun onError(exc: ImageCaptureException) {
                        emitter.onError(exc)
                    }

                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        val photoUri = Uri.fromFile(photoFile)
                        emitter.onNext(photoUri)
                        emitter.onComplete()
                    }
                })
        }
    }

    private fun getLocationObservable(context: Context): Observable<Optional<Location>> {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return ReactiveLocationProvider(context)
                .getUpdatedLocation(LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setNumUpdates(1))
                .map { location -> Optional.of(location) }
        }
        return Observable.just(Optional.empty())
    }

    private fun getMagnetometerSensorObservable(context: Context): Observable<Optional<ReactiveSensorEvent>> {
        val sensors = ReactiveSensors(context)
        if (sensors.hasSensor(Sensor.TYPE_MAGNETIC_FIELD)) {
            return sensors
                .observeSensor(Sensor.TYPE_MAGNETIC_FIELD)
                .filter(ReactiveSensorEvent::sensorChanged)
                .map { sensorEvent -> Optional.of(sensorEvent) }
                .toObservable()
        }
        return Observable.just(Optional.empty())
    }

    /*
    OnClear
     */

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

}