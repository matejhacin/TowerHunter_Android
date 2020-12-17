package com.eles.towerhunter.views.activities.splash

import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.LocalStorage

class SplashViewModel(
    private val storage: LocalStorage = LocalStorage
) : ViewModel() {

    val didUserCompleteOnboarding get() = storage.didUserCompleteOnboarding

}