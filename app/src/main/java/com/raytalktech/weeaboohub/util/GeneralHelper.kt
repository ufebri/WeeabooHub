package com.raytalktech.weeaboohub.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.raytalktech.weeaboohub.config.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import android.widget.LinearLayout

import android.view.WindowManager

import android.widget.TextView

import android.view.Gravity

import android.view.ViewGroup
import android.view.Window

import android.widget.ProgressBar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.raytalktech.weeaboohub.R


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
            true -> url?.replace(Constant.BASE_IMAGE_URL, "").toString()
            false -> ""
        }
    }

    fun setFileFormat(url: String): String {
        return when {
            url.contains(".jpg") -> "jpg"
            url.contains(".gif") -> "gif"
            url.contains(".png") -> "png"
            else -> "png"
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

    fun showAlertDialog(context: Context, title: String, message: String, positiveButton: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButton) { dialog, id ->
                dialog.dismiss()
            }
            .setNegativeButton("Deny") { dialog, id ->
                dialog.cancel()
            }
            .show()
    }

    fun setProgressDialog(context: Context, message: String): AlertDialog {
        val llPadding = 30
        val ll = LinearLayout(context)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(context)
        tvText.text = message
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.textSize = 20.toFloat()
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setView(ll)

        val dialog = builder.create()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window?.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window?.attributes = layoutParams
        }
        return dialog
    }

    fun Activity.showSnackBar(msg: String) {
        Snackbar.make(this.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
            .setAction("OK") {
            }
            .show()
    }

    fun Fragment.showSnackBar(msg: String) {
        Snackbar.make(
            this.requireView().rootView,
            msg,
            Snackbar.LENGTH_SHORT
        )
            .setAction("OK") {
            }
            .show()
    }
}