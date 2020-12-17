package com.eles.towerhunter.views.vegetationStateQuestionFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.LocalStorage
import com.eles.towerhunter.data.VegetationState
import com.eles.towerhunter.helpers.SingleLiveEvent
import kotlinx.coroutines.delay

class VegetationStateQuestionViewModel(
    private val storage: LocalStorage = LocalStorage
) : ViewModel() {

    private val _didUploadData = SingleLiveEvent<Boolean>()
    val didUploadData: LiveData<Boolean> get() = _didUploadData

    fun uploadData(state: VegetationState) {
        _didUploadData.value = true
    }

}