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

    var vegetationState: String? = null

    private val uriString: String = uri.toString()
    val geoLocation: GeoLocation? = if (location != null) GeoLocation(location.latitude, location.longitude) else null
    val magnetometer: MagnetometerValues? = if (magnetometerValues != null) MagnetometerValues(magnetometerValues[0], magnetometerValues[1], magnetometerValues[2]) else null

    val uri: Uri?
        get() = Uri.parse(uriString)

    fun cleanUp() {
        if (uri != null) {
            val file = File(uri!!.path!!)
            if (file.exists()) {
                file.delete()
            }
        }
    }

}