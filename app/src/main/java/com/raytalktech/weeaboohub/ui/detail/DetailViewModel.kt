package com.raytalktech.weeaboohub.ui.detail

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.raytalktech.weeaboohub.R
import com.raytalktech.weeaboohub.config.Constant
import com.raytalktech.weeaboohub.data.source.DataRepository
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.raytalktech.weeaboohub.util.GeneralHelper
import java.io.InputStream
import java.net.URL

class DetailViewModel(private val dataRepository: DataRepository) : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    private lateinit var mContext: Context
    private val action = MutableLiveData<String>()
    private val dataID = MutableLiveData<String>()
    private lateinit var getURLImage: URL

    fun selectedAction(action: String, context: Context) {
        this.action.value = action
        this.mContext = context
    }

    fun selectedID(dataID: String) {
        this.dataID.value = dataID
    }

    var getDataByID: LiveData<DataMainEntity> =
        Transformations.switchMap(dataID) { mDataID -> dataRepository.getDetailDataByID(mDataID) }

    fun actionOperations() {
        val url: String = getDataByID.value?.imgSrc ?: ""
        getURLImage = URL(url)
        val getURLImageString: String = url
        val fileName: String = GeneralHelper.generateFileName(getDataByID.value?.imgSrc)

        when (action.value) {
            Constant.listActionAdapter[0] -> dataRepository.downloadImage(getURLImageString, fileName)
            Constant.listActionAdapter[1] -> GeneralHelper.shareIntent(
                mContext, mContext.getString(
                    R.string.intent_message
                ), mContext.getString(R.string.intent_title), getURLImageString
            )
            Constant.listActionAdapter[2] -> addToBookmark()
            Constant.listActionAdapter[3] -> setWallpaper()
            else -> {}
        }
    }


    private fun addToBookmark() {
        val dataResource = getDataByID.value
        if (dataResource != null) {
            val newState = !dataResource.isFavorite
            dataRepository.addToBookmark(dataResource, newState)
        }
    }

    private fun setWallpaper() {
        val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(mContext)
        try {
            val ins: InputStream = getURLImage.openStream()
            wallpaperManager.setStream(ins)
        } catch (e: Exception) {
            GeneralHelper.showToastMessage(mContext, e.localizedMessage ?: "")
        }
    }
}