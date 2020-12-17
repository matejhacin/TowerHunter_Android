package com.eles.towerhunter.views.photoConfirmation

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.LocalStorage

class ConfirmPhotoViewModel(
    private val storage: LocalStorage = LocalStorage
) : ViewModel() {

    val photoUri: Uri?
        get() = storage.lastPhotoUri

}