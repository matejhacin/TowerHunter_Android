package com.eles.towerhunter.network

import com.eles.towerhunter.data.dto.ImageMetaDataDTO
import com.eles.towerhunter.data.dto.ImageUrlDTO
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url

interface Api {

    @POST("image")
    fun createImage(@Body image: ImageMetaDataDTO): Call<ImageUrlDTO>

    @PUT
    fun uploadImage(@Url url: String, @Body body: RequestBody): Call<Unit>

}