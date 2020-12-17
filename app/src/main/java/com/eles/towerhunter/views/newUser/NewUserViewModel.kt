package com.eles.towerhunter.views.newUser

import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.LocalStorage

class NewUserViewModel(
    private val storage: LocalStorage = LocalStorage
) : ViewModel() {

    fun updateUserQualificationConfiguration(isQualified: Boolean) {
        storage.isUserQualified = isQualified
    }

}