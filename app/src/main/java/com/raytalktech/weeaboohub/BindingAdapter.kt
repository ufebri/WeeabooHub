package com.raytalktech.weeaboohub

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raytalktech.weeaboohub.network.model.DataResponse
import com.raytalktech.weeaboohub.network.model.DataResponseModified
import com.raytalktech.weeaboohub.ui.adapter.PhotoGridAdapter
import com.raytalktech.weeaboohub.ui.home.ApiStatus
import com.raytalktech.weeaboohub.ui.home.ApiStatus.*

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DataResponse>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    if (data != null) {
        adapter.submitList(getDataResponse(data[0]))
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

private fun getDataResponse(_photos: DataResponse): List<DataResponseModified> {
    val mData: ArrayList<DataResponseModified> = arrayListOf()
    for (index in _photos.files.indices) {
        mData.add(DataResponseModified("{$index}", _photos.files[index]))
    }
    return mData
}
