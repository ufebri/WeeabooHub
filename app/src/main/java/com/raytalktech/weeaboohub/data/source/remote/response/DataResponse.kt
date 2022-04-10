package com.raytalktech.weeaboohub.data.source.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataResponse(
    @Json(name = "files")
    val files: List<String>
) : Parcelable

data class DataResponseModified(
    val id: String,
    val imgSrc: String
)
