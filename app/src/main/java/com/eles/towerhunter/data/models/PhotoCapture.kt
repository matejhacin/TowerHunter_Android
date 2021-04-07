package com.eles.towerhunter.data.models

import android.location.Location
import android.net.Uri
import java.io.File

/**
 * Used to hold a reference to photo that was taken at some point with all meta data attached.
 */
class PhotoCapture(
    uri: Uri,
    location: Location?,
    magnetometerValues: FloatArray?,
) {

    private val uriString: String = uri.toString()
    val geoLocation: GeoLocation? = if (location != null) GeoLocation(location.latitude, location.longitude) else null
    val magnetometer: MagnetometerValues? = if (magnetometerValues != null) MagnetometerValues(magnetometerValues[0], magnetometerValues[1], magnetometerValues[2]) else null
    var vegetationState: String? = null

    val uri: Uri?
        get() = Uri.parse(uriString)

    val file: File?
        get() = fileFromUri()

    val isReadyForUpload: Boolean
        get() {
            return geoLocation != null
                    && magnetometer != null
                    && file?.exists() == true
        }

    fun cleanUp() {
        if (file?.exists() == true) {
            file?.delete()
        }
    }

    private fun fileFromUri(): File? {
        val uriPath = uri?.path ?: return null
        return try {
            File(uriPath)
        } catch (e: Exception) {
            null
        }
    }

}