package com.eles.towerhunter.views.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.eles.towerhunter.views.activities.MainActivity
import com.eles.towerhunter.views.activities.OnboardingActivity

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Navigate to onboarding or main view
        startActivity(
            when (viewModel.didUserCompleteOnboarding) {
                true -> Intent(this, MainActivity::class.java)
                false -> Intent(this, OnboardingActivity::class.java)
            }
        ).also { finish() }
    }
}