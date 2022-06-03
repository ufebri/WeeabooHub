package com.raytalktech.weeaboohub.config

import com.raytalktech.weeaboohub.R
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity

object Constant {

    /**
     * Constant Value for DATA
     */
    const val BASE_URL: String = "https://api.waifu.pics/"
    const val BASE_IMAGE_URL: String = "https://i.waifu.pics/"

    /**
     * Constant Value For Type
     */
    val listType: List<String> = listOf("sfw")

    /**
     * Constant Value For Category
     */
    val listCategory: List<String> = listOf(
        "waifu",
        "neko",
        "shinobu",
        "megumin",
        "bully",
        "cuddle",
        "cry",
        "hug",
        "awoo",
        "kiss",
        "lick",
        "pat",
        "smug",
        "bonk",
        "yeet",
        "blush",
        "smile",
        "wave",
        "highfive",
        "handhold",
        "nom",
        "bite",
        "glomp",
        "slap",
        "kill",
        "kick",
        "happy",
        "wink",
        "poke",
        "dance",
        "cringe"
    )

    /**
     * Constant Value For Action BottomSheet sorter by [listActionIconAdapter]
     */
    val listActionAdapter: List<String> = listOf("Download", "Share", "Favorite", "Set Wallpaper")

    /**
     * Constant Value For Icon Action BottomSheet sorter by [listActionAdapter]
     */
    val listActionIconAdapter: List<Int> =
        arrayListOf(
            R.drawable.ic_download,
            R.drawable.ic_share,
            R.drawable.ic_bookmark,
            R.drawable.ic_set_wallpaper
        )

    /**
     * Constant Value BlankView
     */
    val listBlankDataView: List<DataMainEntity> = arrayListOf(
        DataMainEntity("", "", "blank", "", "", "")
    )

    enum class RESPONSE_CODE {
        NO_DATA,
        NO_CONNECTION
    }
}