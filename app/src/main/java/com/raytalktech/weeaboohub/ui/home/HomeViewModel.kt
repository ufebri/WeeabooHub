package com.raytalktech.weeaboohub.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raytalktech.weeaboohub.network.WeeaBooHubApi
import com.raytalktech.weeaboohub.network.model.DataResponse
import com.raytalktech.weeaboohub.network.model.DataResponseModified
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class HomeViewModel : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _photos = MutableLiveData<List<DataResponse>>()
    val photos: LiveData<List<DataResponse>> = _photos

    init {
        getPhotos()
    }

    private fun getPhotos() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _photos.value = listOf(WeeaBooHubApi.retrofitService.getPhotos())
                _status.value = ApiStatus.DONE
                //getDataResponse()
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Log.d("error", "getPhotos: " + e.localizedMessage)
            }
        }
    }
}