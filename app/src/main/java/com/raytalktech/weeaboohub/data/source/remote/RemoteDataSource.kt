package com.raytalktech.weeaboohub.data.source.remote

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raytalktech.weeaboohub.R
import com.raytalktech.weeaboohub.data.source.remote.response.DataResponse
import com.raytalktech.weeaboohub.network.ApiConfig
import com.raytalktech.weeaboohub.util.GeneralHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors


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