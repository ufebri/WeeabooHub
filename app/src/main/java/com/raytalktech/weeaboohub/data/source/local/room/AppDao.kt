package com.raytalktech.weeaboohub.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity


@Dao
interface AppDao {

    @Query("SELECT * FROM datamainentity WHERE type = :type AND category = :category")
    fun getAllListData(type: String, category: String): LiveData<List<DataMainEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: List<DataMainEntity>)

    @Query("SELECT * FROM datamainentity WHERE id = :id")
    fun getDetailDataWithID(id: String): LiveData<DataMainEntity>

    @Update
    fun updateDetailData(data: DataMainEntity)

    @Query("SELECT * FROM datamainentity WHERE isFavorite = 1")
    fun getBookmarkList(): LiveData<List<DataMainEntity>>

}