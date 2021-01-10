package com.eles.towerhunter.views.vegetationStateQuestionFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.LocalStorage
import com.eles.towerhunter.data.VegetationState
import com.eles.towerhunter.helpers.SingleLiveEvent
import com.eles.towerhunter.network.clients.ImageUploadClient
import kotlinx.coroutines.delay

class VegetationStateQuestionViewModel(
    private val storage: LocalStorage = LocalStorage,
    private val uploadClient: ImageUploadClient = ImageUploadClient()
) : ViewModel() {

    private val _didUploadData = SingleLiveEvent<Boolean>()
    val didUploadData: LiveData<Boolean> get() = _didUploadData

    private val photoCapture get() = storage.lastPhotoCapture

    fun uploadData(state: VegetationState) {
        if (photoCapture == null) {
            _didUploadData.value = false
            return
        }

        uploadClient.createImage(photoCapture!!, state) { success ->
            _didUploadData.value = success
        }
    }

}