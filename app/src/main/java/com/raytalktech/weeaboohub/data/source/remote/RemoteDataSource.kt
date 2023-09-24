package com.raytalktech.weeaboohub.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raytalktech.weeaboohub.data.source.remote.response.DataResponse
import com.raytalktech.weeaboohub.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteDataSource {

    companion object {
        private val client = ApiConfig.getApiService()
        private const val TAG = "RemoteDataSource"


        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getData(type: String, category: String): LiveData<ApiResponse<DataResponse>> {
        val resultItem = MutableLiveData<ApiResponse<DataResponse>>()
        client.getPhotos(type = type, category = category).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                if (response.isSuccessful)
                    resultItem.value = ApiResponse.success(response.body() as DataResponse)
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
        return resultItem
    }
}