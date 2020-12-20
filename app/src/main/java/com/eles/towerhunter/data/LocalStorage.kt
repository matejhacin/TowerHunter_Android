package com.eles.towerhunter.data

import android.content.Context
import android.content.SharedPreferences
import com.eles.towerhunter.data.models.PhotoCapture
import com.eles.towerhunter.helpers.gson

private const val SP_NAME = "userConfigPreferences"
private const val SP_ONBOARDING_COMPLETED = "onboardingCompleted"
private const val SP_USER_QUALIFIED = "isUserQualified"
private const val SP_LAST_PHOTO_CAPTURE = "lastPhotoUri"

/**
 * A simple storage solution that works exclusively on SharedPreferences at the time of writing this.
 * Used for storing all kinds of data, from User preferences to Photo paths.
 * Use this for as long as the app is simple. For a scalable solution, consider implementing
 * another kind of storage solution.
 */
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

    var lastPhotoCapture: PhotoCapture?
        set(value) {
            val serializedData = gson.toJson(value)
            sharedPreferences.edit().putString(SP_LAST_PHOTO_CAPTURE, serializedData).apply()
        }
        get() {
            val serializedData = sharedPreferences.getString(SP_LAST_PHOTO_CAPTURE, null)
            if (serializedData != null) {
                return try {
                    gson.fromJson<PhotoCapture>(serializedData, PhotoCapture::class.java)
                } catch (e: Exception) {
                    null
                }
            }
            return null
        }

}