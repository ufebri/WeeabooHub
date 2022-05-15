package com.raytalktech.weeaboohub.util

import android.app.WallpaperManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SetWallpaper(private val context: Context) {

    private var callBack: (Boolean) -> Unit = {}

    fun go(bitmap: Bitmap) {
        val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(context)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val height: Int = Resources.getSystem().displayMetrics.heightPixels
                    val width: Int = Resources.getSystem().displayMetrics.widthPixels

                    val start = Point(0, 0)
                    val end = Point(bitmap.width, bitmap.height)

                    if (bitmap.width > width) {
                        start.x = ((bitmap.width) - width) / 2
                        end.x = start.x + width
                    }

                    if (bitmap.height > height) {
                        start.y = ((bitmap.height) - height) / 2
                        end.y = start.y + height
                    }

                    delay(1000)
                    wallpaperManager.setBitmap(bitmap, Rect(start.x, start.y, end.x, end.y), false)
                    callBack(true)
                } else {
                    delay(1000)
                    wallpaperManager.setBitmap(bitmap)
                    callBack(true)
                }
            } catch (e: Exception) {
                Log.d("TAG", "go: " + e.localizedMessage)
                callBack(false)
            }
        }
    }

    fun getResult(callback: (Boolean) -> Unit) {
        this.callBack = callback
    }
}