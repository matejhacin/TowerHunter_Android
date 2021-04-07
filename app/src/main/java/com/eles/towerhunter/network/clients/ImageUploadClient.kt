package com.eles.towerhunter.network.clients

import com.eles.towerhunter.data.VegetationState
import com.eles.towerhunter.data.dto.ImageMetaDataDTO
import com.eles.towerhunter.data.dto.ImageUrlDTO
import com.eles.towerhunter.data.models.PhotoCapture
import com.eles.towerhunter.network.RestClient

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ImageUploadClient {

    fun createImage(image: PhotoCapture, onComplete: ((Boolean, Throwable?) -> Unit)) {
        val dto = ImageMetaDataDTO(
                image.geoLocation?.latitude!!,
                image.geoLocation.longitude,
                image.magnetometer?.x!!,
                image.magnetometer.y,
                image.magnetometer.z,
                image.vegetationState ?: VegetationState.unknown.toString()
        )

        RestClient.api.createImage(dto).enqueue(object : Callback<ImageUrlDTO> {
            override fun onResponse(call: Call<ImageUrlDTO>, response: Response<ImageUrlDTO>) {
                when (response.isSuccessful) {
                    true -> uploadImage(response.body()!!.url, image, onComplete)
                    false -> onComplete(false, null)
                }
            }

            override fun onFailure(call: Call<ImageUrlDTO>, t: Throwable) {
                onComplete(false, t)
            }
        })
    }

    private fun uploadImage(uploadUrl: String, image: PhotoCapture, onComplete: ((Boolean, Throwable?) -> Unit)) {
        if (image.file == null) {
            onComplete(false, Exception("Tried to upload an image that does not exist."))
            return
        }

        val body = image.file!!.asRequestBody("image/jpeg".toMediaType())

        RestClient.api.uploadImage(uploadUrl, body).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                onComplete(response.isSuccessful, null)
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                onComplete(false, t)
            }
        })
    }

}