package com.eles.towerhunter.helpers.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.eles.towerhunter.R

fun Fragment.requireAppCompatActivity(): AppCompatActivity {
    return requireActivity() as AppCompatActivity
}

fun Fragment.configureToolbar(showToolbar: Boolean, showBackButton: Boolean, backButtonIconSrc: Int? = null) {
    val toolbar = requireAppCompatActivity().supportActionBar

    // Change visibility
    if (showToolbar) {
        toolbar?.show()
    } else {
        toolbar?.hide()
    }

    // Change back button icon and visibility
    if (backButtonIconSrc != null) {
        toolbar?.setHomeAsUpIndicator(backButtonIconSrc)
    } else {
        toolbar?.setHomeAsUpIndicator(null)
    }

    // Change back button visibility
    toolbar?.setDisplayHomeAsUpEnabled(showBackButton)
}