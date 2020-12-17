package com.eles.towerhunter.views.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.LocalStorage
import com.eles.towerhunter.helpers.SingleLiveEvent
import java.io.File
import java.util.*

private const val PERMISSION_CAMERA = Manifest.permission.CAMERA
private const val FOLDER_NAME = "TowerHunter"
private const val FILE_NAME = "photo_%s.jpg"

class CameraViewModel(
    private val storage: LocalStorage = LocalStorage
) : ViewModel() {

    private val _cameraPermissionGranted = SingleLiveEvent<Boolean>()
    val cameraPermissionGranted: LiveData<Boolean> get() = _cameraPermissionGranted

    private var outputDirectory: File? = null
    private var cameraPermissionLauncher: ActivityResultLauncher<String>? = null

    fun prepareActivityResult(fragment: Fragment) {
        cameraPermissionLauncher = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            _cameraPermissionGranted.value = granted
        }
    }

    fun preparePhotoFile(activity: Activity): File {
        // Create directory if it doesn't exist yet
        if (outputDirectory == null) {
            val rootDir = activity.externalMediaDirs.firstOrNull() ?: activity.filesDir
            outputDirectory = File(rootDir, FOLDER_NAME).apply { mkdirs() }
        }
        // Prepare File that the photo will save into
        return File(outputDirectory, FILE_NAME.format(Date().time.toString()))
    }

    fun checkCameraPermission(context: Context) = ContextCompat.checkSelfPermission(
        context,
        PERMISSION_CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    fun requestCameraPermission() {
        cameraPermissionLauncher?.launch(Manifest.permission.CAMERA)
    }

    fun savePhotoUri(uri: Uri) {
        storage.lastPhotoUri = uri
    }

}