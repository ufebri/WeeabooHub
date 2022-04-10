package com.raytalktech.weeaboohub.util

import android.content.Context
import com.raytalktech.weeaboohub.data.source.DataRepository
import com.raytalktech.weeaboohub.data.source.local.LocalDataSource
import com.raytalktech.weeaboohub.data.source.local.room.AppDatabase
import com.raytalktech.weeaboohub.data.source.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): DataRepository {
        val database = AppDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.jetMovieDao())
        val appExecutors = AppExecutors()
        return DataRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}