package com.raytalktech.weeaboohub.network.model

import com.squareup.moshi.Json

data class DataResponse(
    @Json(name = "files")
    val files: List<String>
)

data class DataResponseModified(
    val id: String,
    val imgSrc: String
)
