package com.eles.towerhunter.views.newUser

import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.UserConfiguration

class NewUserViewModel : ViewModel() {

    fun updateUserQualificationConfiguration(isQualified: Boolean) {
        UserConfiguration.isQualified = isQualified
    }

}