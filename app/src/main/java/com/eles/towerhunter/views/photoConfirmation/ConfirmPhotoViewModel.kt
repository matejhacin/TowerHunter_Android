package com.eles.towerhunter.views.photoConfirmation

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.LocalStorage
import com.eles.towerhunter.data.models.PhotoCapture

class ConfirmPhotoViewModel(
    private val storage: LocalStorage = LocalStorage
) : ViewModel() {

    private val photoCapture: PhotoCapture?
        get() = storage.lastPhotoCapture

    val photoUri: Uri?
        get() = photoCapture?.uri

    fun retakePhotoClicked() {
        photoCapture?.cleanUp()
    }

}