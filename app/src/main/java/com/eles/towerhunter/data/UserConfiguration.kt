package com.eles.towerhunter.data

import android.content.Context
import android.content.SharedPreferences

private const val SP_NAME = "userConfigPreferences"
private const val SP_ONBOARDING_COMPLETED = "onboardingCompleted"
private const val SP_USER_QUALIFIED = "isUserQualified"

object UserConfiguration {

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    /*
    Setters and Getters
     */

    // Determines if user has seen and completed all one-time onboarding views
    var didCompleteOnboarding: Boolean
        set(value) = sharedPreferences.edit().putBoolean(SP_ONBOARDING_COMPLETED, value).apply()
        get() = sharedPreferences.getBoolean(SP_ONBOARDING_COMPLETED, false)

    // Determines if user is qualified to assess state of vegetation
    var isQualified: Boolean
        set(value) = sharedPreferences.edit().putBoolean(SP_USER_QUALIFIED, value).apply()
        get() = sharedPreferences.getBoolean(SP_USER_QUALIFIED, false)

}