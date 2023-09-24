package com.raytalktech.weeaboohub.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.raytalktech.weeaboohub.data.source.DataRepository
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import java.net.URL


class DetailViewModel(private val dataRepository: DataRepository) : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    private lateinit var mContext: Context
    private val dataID = MutableLiveData<String>()
    private lateinit var getURLImage: URL

    fun selectedID(dataID: String) {
        this.dataID.value = dataID
    }

    var getDataByID: LiveData<DataMainEntity> =
        Transformations.switchMap(dataID) { mDataID -> dataRepository.getDetailDataByID(mDataID) }

    fun addToBookmark() {
        val dataResource = getDataByID.value
        if (dataResource != null) {
            val newState = !dataResource.isFavorite
            dataRepository.addToBookmark(dataResource, newState)
        }
    }
}