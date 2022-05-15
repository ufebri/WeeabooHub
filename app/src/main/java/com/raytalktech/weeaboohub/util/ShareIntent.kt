package com.raytalktech.weeaboohub.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.content.FileProvider
import com.raytalktech.weeaboohub.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class ShareIntent(private val mContext: Context) {

    private var callBack: (Boolean) -> Unit = {}

    fun go(message: String, title: String, url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val img: Bitmap
            try {
                val imgURL = URL(url)
                img = BitmapFactory.decodeStream(imgURL.openConnection().getInputStream())

                val file = File(mContext.externalCacheDir, GeneralHelper.generateFileName(url))
                val fOut = FileOutputStream(file)

                img.compress(Bitmap.CompressFormat.PNG, 100, fOut)
                fOut.flush()
                fOut.close()
                file.setReadable(true, false)

                val uri = FileProvider.getUriForFile(
                    mContext,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file
                )

                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra(Intent.EXTRA_TEXT, message)
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                intent.type = "image/png"
                callBack(true)
                delay(1000)
                mContext.startActivity(Intent.createChooser(intent, title))
            } catch (e: Exception) {
                Log.d("TAG", "shareIntent: " + e.localizedMessage)
                callBack(true)
            }
        }
    }

    fun getResult(callBack: (Boolean) -> Unit) {
        this.callBack = callBack
    }
}