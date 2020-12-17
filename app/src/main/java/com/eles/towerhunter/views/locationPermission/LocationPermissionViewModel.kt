package com.eles.towerhunter.views.locationPermission

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eles.towerhunter.data.LocalStorage
import com.eles.towerhunter.helpers.SingleLiveEvent

class LocationPermissionViewModel(
    private val storage: LocalStorage = LocalStorage
) : ViewModel() {

    private var locationPermissionLauncher: ActivityResultLauncher<String>? = null

    private val _isPermissionAccepted = SingleLiveEvent<Boolean>()
    val isPermissionAccepted: LiveData<Boolean> get() = _isPermissionAccepted

    fun prepareActivityResult(fragment: Fragment) {
        locationPermissionLauncher = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            _isPermissionAccepted.value = granted
        }
    }

    fun requestLocationPermission() {
        locationPermissionLauncher?.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    fun markOnboardingCompleted() {
        storage.didUserCompleteOnboarding = true
    }

}