package com.raytalktech.weeaboohub.util

import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.ufebri.androidbaseprime.domain.model.PhotoData

object RemappingData {

    fun remappingResponseData(mListData: List<DataMainEntity>): List<PhotoData> {
        val mList = mutableListOf<PhotoData>()

        for (index in mListData.indices) {
            mList.add(
                PhotoData(
                    id = mListData[index].id,
                    imgSrc = mListData[index].imgSrc
                )
            )
        }

        return mList
    }
}