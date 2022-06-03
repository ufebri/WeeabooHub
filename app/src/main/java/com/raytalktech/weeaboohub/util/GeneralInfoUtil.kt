package com.raytalktech.weeaboohub.util

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import com.raytalktech.weeaboohub.R
import com.raytalktech.weeaboohub.config.Constant

class GeneralInfoUtil(context: Context, RC: Constant.RESPONSE_CODE) {

    val getDrawableImage: Drawable? = when (RC) {
        Constant.RESPONSE_CODE.NO_DATA -> ContextCompat.getDrawable(context, R.drawable.ic_no_data)
        Constant.RESPONSE_CODE.NO_CONNECTION -> ContextCompat.getDrawable(
            context,
            R.drawable.ic_connection_error
        )
    }

    val getButtonTextAction: String = when (RC) {
        Constant.RESPONSE_CODE.NO_DATA -> context.getString(R.string.button_to_explore)
        Constant.RESPONSE_CODE.NO_CONNECTION -> context.getString(R.string.button_OK)
    }

    val getMessage: String = when (RC) {
        Constant.RESPONSE_CODE.NO_DATA -> context.getString(R.string.msg_no_data)
        Constant.RESPONSE_CODE.NO_CONNECTION -> context.getString(R.string.msg_no_connection)
    }

}