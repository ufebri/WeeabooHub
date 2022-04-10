package com.raytalktech.weeaboohub.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raytalktech.weeaboohub.data.source.DataRepository
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.raytalktech.weeaboohub.util.vo.Resource

class HomeViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getAllDataList(type: String, categories: String): LiveData<Resource<List<DataMainEntity>>> =
        dataRepository.getAllDataList(type, categories)
}