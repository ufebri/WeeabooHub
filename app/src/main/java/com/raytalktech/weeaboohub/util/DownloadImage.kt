package com.raytalktech.weeaboohub.util

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.ByteBuffer

class DownloadImage(private val mContext: Context) {

    private var callback: (Boolean) -> Unit = {}
    private val DELAY_DURATION: Long = 2000

    fun go(imageFileName: String, getURLImageString: String, format: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val imageBitmap: Bitmap =
                    Glide.with(mContext)
                        .asBitmap()
                        .load(getURLImageString)
                        .submit()
                        .get()

                val storageDir = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .toString()
                )
                var success = true
                if (!storageDir.exists()) success = storageDir.mkdirs()

                if (success) {
                    val imageFile = File(storageDir, imageFileName)
                    try {
                        val fOut: OutputStream = FileOutputStream(imageFile)
                        when (format) {
                            "jpg" -> {
                                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                                fOut.close()
                                delay(DELAY_DURATION)
                                callback(true)
                            }
                            "png" -> {
                                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
                                fOut.close()
                                delay(DELAY_DURATION)
                                callback(true)
                            }
                            "gif" -> saveGifImage(getURLImageString, imageFile)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        delay(DELAY_DURATION)
                        callback(false)
                    }
                }
            } catch (e: Exception) {
                delay(DELAY_DURATION)
                callback(false)
                Log.d("TAGE", "go: " + e.message)
            }
        }
    }

    private fun saveGifImage(url: String, imageFile: File) {
        try {
            val imageBitmap: GifDrawable =
                Glide.with(mContext)
                    .asGif()
                    .load(url)
                    .submit()
                    .get()
            val byteBuffer = imageBitmap.buffer
            val output = FileOutputStream(imageFile)
            val bytes = ByteArray(byteBuffer.capacity())
            (byteBuffer.duplicate().clear() as ByteBuffer).get(bytes)
            output.write(bytes, 0, bytes.size)
            output.close()
            callback(true)
        } catch (e: Exception) {
            Log.d("TAGE", "saveGifImage: " + e.localizedMessage)
            callback(false)
        }
    }

    fun getResult(callBack: (Boolean) -> Unit ) {
        this.callback = callBack
    }
}