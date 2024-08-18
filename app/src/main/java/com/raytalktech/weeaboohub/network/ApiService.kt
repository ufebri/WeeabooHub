package com.raytalktech.weeaboohub.network

import com.raytalktech.weeaboohub.data.source.remote.response.DataResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("many/{type}/{category}")
    fun getPhotos(
        @Field("exclude") exclude: String = "",
        @Path("type") type: String,
        @Path("category") category: String
    ): Call<DataResponse>
}