package com.raytalktech.weeaboohub.data.source

import androidx.lifecycle.LiveData
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.raytalktech.weeaboohub.util.vo.Resource

interface DataSource {

    fun getAllDataList(type: String, category: String): LiveData<Resource<List<DataMainEntity>>>

    fun getDetailDataByID(id: String): LiveData<DataMainEntity>

    fun addToBookmark(dataMainEntity: DataMainEntity, state: Boolean)

    fun getBookmarkList(): LiveData<List<DataMainEntity>>
}