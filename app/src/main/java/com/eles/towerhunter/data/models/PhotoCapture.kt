package com.eles.towerhunter.data.models

import android.location.Location
import android.net.Uri

/**
 * Used to hold a reference to photo that was taken at some point with all meta data attached.
 */
class PhotoCapture(
    uri: Uri,
    location: Location?,
    magnetometerValues: FloatArray?
) {

    private val uriString: String = uri.toString()
    val geoLocation: GeoLocation? = if (location != null) GeoLocation(location.latitude, location.longitude) else null
    val magnetometer: MagnetometerValues? = if (magnetometerValues != null) MagnetometerValues(magnetometerValues[0], magnetometerValues[1], magnetometerValues[2]) else null

    val uri: Uri?
        get() = Uri.parse(uriString)

}