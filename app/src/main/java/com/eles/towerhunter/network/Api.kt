package com.eles.towerhunter.network

import com.eles.towerhunter.data.dto.ImageMetaDataDTO
import com.eles.towerhunter.data.dto.ImageUrlDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("image")
    fun createImage(@Body image: ImageMetaDataDTO): Call<ImageUrlDTO>

}