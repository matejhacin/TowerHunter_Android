package com.eles.towerhunter.network.clients

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.OkHttpResponseListener
import com.eles.towerhunter.data.VegetationState
import com.eles.towerhunter.data.dto.ImageMetaDataDTO
import com.eles.towerhunter.data.dto.ImageUrlDTO
import com.eles.towerhunter.data.models.PhotoCapture
import com.eles.towerhunter.network.RestClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ImageUploadClient {

    fun createImage(image: PhotoCapture, onComplete: ((Boolean) -> Unit)) {
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
                    false -> onComplete(false)
                }
            }

            override fun onFailure(call: Call<ImageUrlDTO>, t: Throwable) {
                onComplete(false)
            }
        })
    }

    private fun uploadImage(uploadUrl: String, image: PhotoCapture, onComplete: ((Boolean) -> Unit)) {
        val file = File(image.uri?.path!!)
        AndroidNetworking.put(uploadUrl)
                .addFileBody(file)
                .setPriority(Priority.HIGH)
                .setContentType("image/jpeg")
                .build()
                .getAsOkHttpResponse(object : OkHttpResponseListener {
                    override fun onResponse(response: okhttp3.Response?) {
                        onComplete(response?.isSuccessful == true)
                    }

                    override fun onError(anError: ANError?) {
                        onComplete(false)
                    }
                })
    }

}