package com.eles.towerhunter.views.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*

private const val PERMISSION_CAMERA = Manifest.permission.CAMERA
private const val FOLDER_NAME = "TowerHunter"
private const val FILE_NAME = "photo_%s.jpg"

class CameraViewModel : ViewModel() {

    private var outputDirectory: File? = null

    fun preparePhotoFile(activity: Activity): File {
        // Create directory if it doesn't exist yet
        if (outputDirectory == null) {
            val rootDir = activity.externalMediaDirs.firstOrNull() ?: activity.filesDir
            outputDirectory = File(rootDir, FOLDER_NAME).apply { mkdirs() }
        }
        // Prepare File that the photo will save into
        return File(outputDirectory, FILE_NAME.format(Date().time.toString()))
    }

    fun cameraPermissionGranted(context: Context) = ContextCompat.checkSelfPermission(
        context,
        PERMISSION_CAMERA
    ) == PackageManager.PERMISSION_GRANTED

}