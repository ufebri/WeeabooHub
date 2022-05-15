package com.raytalktech.weeaboohub.network

import com.raytalktech.weeaboohub.data.source.remote.response.DataResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("many/{type}/{category}")
    fun getPhotos(
        @Field("exclude") exclude: String = "",
        @Path("type") type: String,
        @Path("category") category: String
    ): Call<DataResponse>
}