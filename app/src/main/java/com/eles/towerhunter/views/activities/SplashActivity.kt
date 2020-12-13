package com.eles.towerhunter.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start Main Activity
        startActivity(
                Intent(this, MainActivity::class.java)
        )
        finish()
    }
}