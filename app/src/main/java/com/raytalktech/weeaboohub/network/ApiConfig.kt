package com.raytalktech.weeaboohub.network

import com.raytalktech.weeaboohub.BuildConfig
import com.raytalktech.weeaboohub.config.Constant
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {

            /**
             * Enable Logging
             *
             * When Build Type is not Debug, logging will disable
             */
            val loggingInterceptor =
                HttpLoggingInterceptor()

            if (BuildConfig.BUILD_TYPE == "debug")
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            else
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            /**
             * Read JSON Parsing Field With Moshi
             */
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            /**
             * Retrofit config
             */
            val retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}