package com.raytalktech.weeaboohub.data.source.local

import androidx.lifecycle.LiveData
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.raytalktech.weeaboohub.data.source.local.room.AppDao

class LocalDataSource(private val mAppDao: AppDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(jetMovieDao: AppDao): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSource(jetMovieDao)
            }
            return INSTANCE as LocalDataSource
        }
    }

    fun getAllListData(type: String, category: String): LiveData<List<DataMainEntity>> =
        mAppDao.getAllListData(type, category)

    fun insertData(dataMainEntity: List<DataMainEntity>) = mAppDao.insertData(dataMainEntity)

    fun getDetailDataWithID(id: String): LiveData<DataMainEntity> = mAppDao.getDetailDataWithID(id)

    fun updateDetailData(dataMainEntity: DataMainEntity, newState: Boolean) {
        dataMainEntity.isFavorite = newState
        mAppDao.updateDetailData(dataMainEntity)
    }

    fun getBookmarkList(): LiveData<List<DataMainEntity>> = mAppDao.getBookmarkList()
}