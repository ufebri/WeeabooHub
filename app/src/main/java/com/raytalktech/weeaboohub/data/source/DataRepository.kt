package com.raytalktech.weeaboohub.data.source

import androidx.lifecycle.LiveData
import com.raytalktech.weeaboohub.data.NetworkBoundResource
import com.raytalktech.weeaboohub.data.source.local.LocalDataSource
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.raytalktech.weeaboohub.data.source.remote.ApiResponse
import com.raytalktech.weeaboohub.data.source.remote.RemoteDataSource
import com.raytalktech.weeaboohub.data.source.remote.response.DataResponse
import com.raytalktech.weeaboohub.util.AppExecutors
import com.raytalktech.weeaboohub.util.UtilHelper
import com.raytalktech.weeaboohub.util.vo.Resource

class DataRepository private constructor(
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
    private val remoteDataSource: RemoteDataSource
) :
    DataSource {

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(
                    localDataSource,
                    appExecutors,
                    remoteData
                )
            }
    }

    override fun getAllDataList(
        type: String,
        category: String
    ): LiveData<Resource<List<DataMainEntity>>> {
        return object :
            NetworkBoundResource<List<DataMainEntity>, DataResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<DataMainEntity>> =
                localDataSource.getAllListData(type, category)

            override fun shouldFetch(data: List<DataMainEntity>?): Boolean =
                data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<DataResponse>> =
                remoteDataSource.getData(type, category)

            override fun saveCallResult(data: DataResponse) {
                val listData = ArrayList<DataMainEntity>()
                for (response in data.files.indices) {
                    with(response) {
                        val mData = DataMainEntity(
                            id = UtilHelper.generateID(data.files[response]),
                            type = type,
                            category = category,
                            dateModified = UtilHelper.getDateNow(),
                            imgSrc = data.files[response],
                            format = UtilHelper.setFileFormat(data.files[response])
                        )
                        listData.add(mData)
                    }
                }
                localDataSource.insertData(listData)
            }
        }.asLiveData()
    }


    override fun getDetailDataByID(id: String): LiveData<DataMainEntity> =
        localDataSource.getDetailDataWithID(id)


    override fun getBookmarkList(): LiveData<List<DataMainEntity>> =
        localDataSource.getBookmarkList()

    override fun addToBookmark(dataMainEntity: DataMainEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.updateDetailData(dataMainEntity, state) }

}