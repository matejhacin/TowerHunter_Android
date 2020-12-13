package com.eles.towerhunter.views.camera

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.eles.towerhunter.R
import com.eles.towerhunter.databinding.FragmentCameraBinding
import com.eles.towerhunter.views.activities.MainActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment() {

    private val viewModel: CameraViewModel by viewModels()
    private var _binding: FragmentCameraBinding? = null
    private val views get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCamera()
        initClickListener()
    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity() as MainActivity
        activity.supportActionBar?.hide()
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
    }

    /*
    UI
     */

    private fun initClickListener() {
        views.cameraCaptureButton.setOnClickListener { takePhoto() }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    /*
    Camera
     */

    private val imageCapture = ImageCapture.Builder().build()

    private fun initCamera() {
        if (viewModel.cameraPermissionGranted(requireContext())) {
            // Start camera if permissions are OK
            startCamera()
        } else {
            // Request permissions if they're missing
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (granted) {
                    startCamera()
                } else {
                    Toast.makeText(requireContext(), R.string.camera_missing_permission_warning, Toast.LENGTH_LONG).show()
                }
            }.launch(Manifest.permission.CAMERA)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(views.viewFinder.surfaceProvider)
            }
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture)

            } catch(exc: Exception) {}

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val photoFile = viewModel.preparePhotoFile(requireActivity())
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(requireContext()), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    showError("Photo capture failed")
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    views.lastPhotoImageView.setImageURI(savedUri)
                }
            })
    }

}