package com.raytalktech.weeaboohub.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raytalktech.weeaboohub.data.source.DataRepository
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity

class BookmarkViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getBookmarkList(): LiveData<List<DataMainEntity>> = dataRepository.getBookmarkList()
}