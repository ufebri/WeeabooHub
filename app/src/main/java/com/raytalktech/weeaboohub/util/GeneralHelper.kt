package com.raytalktech.weeaboohub.util

import android.content.Context
import android.widget.Toast
import com.raytalktech.weeaboohub.config.Constant
import java.text.SimpleDateFormat
import java.util.*

object GeneralHelper {

    fun generateID(url: String): String {
        val id: String = url
        return when {
            id.contains(".jpg") -> id.replace(Constant.BASE_IMAGE_URL, "").replace(".jpg", "")
            id.contains(".png") -> id.replace(Constant.BASE_IMAGE_URL, "").replace(".png", "")
            id.contains(".gif") -> id.replace(Constant.BASE_IMAGE_URL, "").replace(".gif", "")
            else -> id.replaceAfter(Constant.BASE_IMAGE_URL, "")
        }
    }

    fun getDateNow(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        return sdf.format(Date())
    }

    fun showToastMessage(mContext: Context, message: String) =
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
}