package com.eles.towerhunter.network

import com.eles.towerhunter.helpers.Constants
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RestClient {

    var api: Api

    private val client: OkHttpClient
        get() = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(20L, TimeUnit.SECONDS)
            .readTimeout(20L, TimeUnit.SECONDS)
            .writeTimeout(20L, TimeUnit.SECONDS)
            .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.Network.BASE_URL_STAGING)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    init {
        api = retrofit.create(Api::class.java)
    }

}