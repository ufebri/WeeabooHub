package com.raytalktech.weeaboohub.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class DataMainEntity(
    @PrimaryKey
    @NonNull
    var id: String,
    var imgSrc: String,
    var type: String,
    var category: String,
    var dateModified: String,
    var format: String,
    var isFavorite: Boolean = false
) : Parcelable