package com.eles.towerhunter

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.eles.towerhunter.data.LocalStorage

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        LocalStorage.initialize(applicationContext)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

}