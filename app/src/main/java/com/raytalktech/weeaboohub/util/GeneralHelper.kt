package com.raytalktech.weeaboohub.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.raytalktech.weeaboohub.config.Constant
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
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

    fun generateFileName(url: String?): String {
        return when ((url != null || url != "")) {
            true -> url?.replaceAfter(Constant.BASE_IMAGE_URL, "").toString()
            false -> ""
        }
    }

    fun getDateNow(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        return sdf.format(Date())
    }

    fun showToastMessage(mContext: Context, message: String) =
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()

    @SuppressLint("SetWorldReadable")
    fun shareIntent(mContext: Context, message: String, title: String, url: String) {
        val img: Bitmap

        try {
            val imgURL = URL(url)
            img = BitmapFactory.decodeStream(imgURL.openConnection().getInputStream())

            val file = File(mContext.externalCacheDir, generateFileName(url))
            val fOut = FileOutputStream(file)
            img.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            file.setReadable(true, false)


            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            intent.type = "image/png"
            mContext.startActivity(Intent.createChooser(intent, title))
        } catch (e: Exception) {
            showToastMessage(mContext, e.localizedMessage ?: "")
        }
    }

    fun isPermissionGranted(
        context: Context,
        permission: String
    ): Boolean {
        val isGranted = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val permissionGranted = PackageManager.PERMISSION_GRANTED

            if (ContextCompat.checkSelfPermission(context, permission) != permissionGranted) {
                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity,
                        permission
                    )
                ) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    AlertDialog.Builder(context)
                        .setTitle("Permission required")
                        .setMessage("Permission required to save photos from the Web.")
                        .setPositiveButton("Allow") { dialog, id ->
                            ActivityCompat.requestPermissions(
                                context,
                                arrayOf(permission),
                                1
                            )
                            context.finish()
                        }
                        .setNegativeButton("Deny") { dialog, id -> dialog.cancel() }
                        .show()
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(
                        context,
                        arrayOf(permission),
                        1
                    )
                    // MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.

                }
            }
        }
        return isGranted
    }

    fun mLoad(url: String): Bitmap? {
        val url = URL(url)
        val connection: HttpURLConnection?
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream: InputStream = connection.inputStream
            val bufferedInputStream = BufferedInputStream(inputStream)
            return BitmapFactory.decodeStream(bufferedInputStream)
        } catch (e: IOException) {
            Log.d("mLoad", "mLoad: ${e.printStackTrace()} ")
        }
        return null
    }
}