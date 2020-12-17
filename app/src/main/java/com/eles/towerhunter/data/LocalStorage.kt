package com.eles.towerhunter.data

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri

private const val SP_NAME = "userConfigPreferences"
private const val SP_ONBOARDING_COMPLETED = "onboardingCompleted"
private const val SP_USER_QUALIFIED = "isUserQualified"
private const val SP_LAST_PHOTO_URI = "lastPhotoUri"

object LocalStorage {

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    /*
    User
     */

    // Determines if user has seen and completed all one-time onboarding views
    var didUserCompleteOnboarding: Boolean
        set(value) = sharedPreferences.edit().putBoolean(SP_ONBOARDING_COMPLETED, value).apply()
        get() = sharedPreferences.getBoolean(SP_ONBOARDING_COMPLETED, false)

    // Determines if user is qualified to assess state of vegetation
    var isUserQualified: Boolean
        set(value) = sharedPreferences.edit().putBoolean(SP_USER_QUALIFIED, value).apply()
        get() = sharedPreferences.getBoolean(SP_USER_QUALIFIED, false)

    /*
    Photos
     */

    var lastPhotoUri: Uri?
        set(value) = sharedPreferences.edit().putString(SP_LAST_PHOTO_URI, value.toString()).apply()
        get() {
            val uriString = sharedPreferences.getString(SP_LAST_PHOTO_URI, null)
            if (uriString != null) {
                return Uri.parse(uriString)
            }
            return null
        }

}