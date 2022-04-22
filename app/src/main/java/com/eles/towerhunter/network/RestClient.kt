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
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.Network.BASE_URL_PRODUCTION)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    init {
        api = retrofit.create(Api::class.java)
    }

}