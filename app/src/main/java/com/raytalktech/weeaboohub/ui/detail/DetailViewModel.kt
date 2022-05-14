package com.raytalktech.weeaboohub.ui.detail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.raytalktech.weeaboohub.R
import com.raytalktech.weeaboohub.config.Constant
import com.raytalktech.weeaboohub.data.source.DataRepository
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.raytalktech.weeaboohub.util.GeneralHelper
import com.raytalktech.weeaboohub.util.Permission
import com.raytalktech.weeaboohub.util.PermissionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.nio.ByteBuffer


class DetailViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private lateinit var mFragment: Fragment

    @SuppressLint("StaticFieldLeak")
    private lateinit var mContext: Context
    private val action = MutableLiveData<String>()
    private val dataID = MutableLiveData<String>()
    private lateinit var getURLImage: URL

    fun selectedAction(action: String, mFragment: Fragment) {
        this.action.value = action
        this.mFragment = mFragment
    }

    fun selectedID(dataID: String) {
        this.dataID.value = dataID
    }

    var getDataByID: LiveData<DataMainEntity> =
        Transformations.switchMap(dataID) { mDataID -> dataRepository.getDetailDataByID(mDataID) }

    fun actionOperations() {
        mContext = mFragment.requireContext()
        val url: String = getDataByID.value?.imgSrc ?: ""
        getURLImage = URL(url)
        val getURLImageString: String = url
        val fileName: String = GeneralHelper.generateFileName(getDataByID.value?.imgSrc)
        val format: String = getDataByID.value?.format ?: ""

        when (action.value) {
//            Constant.listActionAdapter[0] -> downloadImage(fileName, getURLImageString, format)
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