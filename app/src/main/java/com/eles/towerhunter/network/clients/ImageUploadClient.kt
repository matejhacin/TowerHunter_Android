package com.eles.towerhunter.network.clients

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
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

    fun createImage(image: PhotoCapture, onSuccess: (() -> Unit)) {
        val dto = ImageMetaDataDTO(
            image.geoLocation?.latitude!!,
            image.geoLocation.longitude,
            image.magnetometer?.x!!,
            image.magnetometer.y,
            image.magnetometer.z
        )

        RestClient.api.createImage(dto).enqueue(object : Callback<ImageUrlDTO> {
            override fun onResponse(call: Call<ImageUrlDTO>, response: Response<ImageUrlDTO>) {
                when (response.isSuccessful) {
//                    true -> uploadImage(response.body()!!.url, image)
                    true -> onSuccess()
                    false -> TODO("Handle network error here")
                }
            }

            override fun onFailure(call: Call<ImageUrlDTO>, t: Throwable) {
                TODO("Handle network error here")
            }
        })
    }

//    private fun uploadImage(uploadUrl: String, image: PhotoCapture) {
//        val file = File(image.uri?.path!!)
//        AndroidNetworking.post(uploadUrl)
//            .addFileBody(file)
//            .setPriority(Priority.HIGH)
//            .addHeaders("Content-Type", "image/jpeg")
//            .build()
//            .getAsOkHttpResponse(object : OkHttpResponseListener {
//                override fun onResponse(response: okhttp3.Response?) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onError(anError: ANError?) {
//                    TODO("Not yet implemented")
//                }
//            })
//    }

    private fun uploadImage(uploadUrl: String, image: PhotoCapture) {
        val file = File(image.uri?.path!!)
        AndroidNetworking.upload(uploadUrl)
            .addMultipartFile("image", file)
            .setPriority(Priority.HIGH)
            .build()
            .setUploadProgressListener { bytesUploaded, totalBytes ->
                Log.d("Image upload" , "$bytesUploaded/$totalBytes")
            }
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    print("break")
                }

                override fun onError(error: ANError) {
                    print("break")
                }
            })
    }

}