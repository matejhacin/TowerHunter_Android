package com.eles.towerhunter.views.vegetationStateQuestionFragment

import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.LocalStorage
import com.eles.towerhunter.data.VegetationState

class VegetationStateQuestionViewModel(
    private val storage: LocalStorage = LocalStorage
) : ViewModel() {

    private val photoCapture = storage.lastPhotoCapture

    fun saveState(state: VegetationState) {
        photoCapture?.vegetationState = state.toString()
        storage.lastPhotoCapture = photoCapture
    }

}