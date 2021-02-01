package com.eles.towerhunter.views.vegetationStateQuestionFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.LocalStorage
import com.eles.towerhunter.data.VegetationState
import com.eles.towerhunter.helpers.SingleLiveEvent
import com.eles.towerhunter.network.clients.ImageUploadClient

class VegetationStateQuestionViewModel(
    private val storage: LocalStorage = LocalStorage,
    private val uploadClient: ImageUploadClient = ImageUploadClient()
) : ViewModel() {

    private val _didUploadData = SingleLiveEvent<Boolean>()
    val didUploadData: LiveData<Boolean> get() = _didUploadData

    private val photoCapture = storage.lastPhotoCapture

    fun uploadData(state: VegetationState) {
        if (photoCapture == null) {
            _didUploadData.value = false
            return
        }

        photoCapture.vegetationState = state.toString()

        uploadClient.createImage(photoCapture) { success ->
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