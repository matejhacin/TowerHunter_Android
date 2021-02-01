package com.eles.towerhunter.views.mainMenu

import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.LocalStorage

class MainMenuViewModel(
    private val storage: LocalStorage = LocalStorage
) : ViewModel() {

    val hasPendingUploads get() = storage.failedUploads.isNotEmpty()

}