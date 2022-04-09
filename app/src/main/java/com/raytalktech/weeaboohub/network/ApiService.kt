package com.raytalktech.weeaboohub.network

import com.raytalktech.weeaboohub.config.Constant
import com.raytalktech.weeaboohub.network.model.DataResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

private val loggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

val client = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constant.BASE_URL)
    .client(client)
    .build()


interface ApiService {
    @FormUrlEncoded
    @POST("many/sfw/neko")
    suspend fun getPhotos(@Field("exclude") exclude: String = ""): DataResponse
}

object WeeaBooHubApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}