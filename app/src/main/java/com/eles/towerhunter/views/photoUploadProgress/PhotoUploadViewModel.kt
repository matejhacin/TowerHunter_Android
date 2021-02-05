package com.eles.towerhunter.views.photoUploadProgress

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.LocalStorage
import com.eles.towerhunter.helpers.SingleLiveEvent
import com.eles.towerhunter.network.clients.ImageUploadClient

class PhotoUploadViewModel(
    private val storage: LocalStorage = LocalStorage,
    private val uploadClient: ImageUploadClient = ImageUploadClient()
) : ViewModel() {

    private val photoCapture = storage.lastPhotoCapture

    private val _didUploadData = SingleLiveEvent<Boolean>()
    val didUploadData: LiveData<Boolean> get() = _didUploadData

    fun beginUpload() {
        val photo = storage.lastPhotoCapture

        if (photo == null) {
            _didUploadData.value = false
            return
        }

        uploadClient.createImage(photo) { success, exception ->
            if (success) {
                deleteLocalFile()
            } else {
                addPhotoCaptureToFailedUploads()
            }
            _didUploadData.value = success
        }
    }

    private fun deleteLocalFile() {
        photoCapture?.cleanUp()
    }

    private fun addPhotoCaptureToFailedUploads() {
        storage.addFailedUpload(photoCapture!!)
    }

}