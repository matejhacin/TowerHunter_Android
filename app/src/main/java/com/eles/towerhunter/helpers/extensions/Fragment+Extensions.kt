package com.eles.towerhunter.helpers.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.requireAppCompatActivity(): AppCompatActivity {
    return requireActivity() as AppCompatActivity
}