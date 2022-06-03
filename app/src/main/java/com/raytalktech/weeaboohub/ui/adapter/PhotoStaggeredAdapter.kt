package com.raytalktech.weeaboohub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.raytalktech.weeaboohub.databinding.StaggeredViewItemBinding
import com.raytalktech.weeaboohub.ui.viewholder.StaggeredImagesViewHolder

class PhotoStaggeredAdapter(private val onClick: CallBackAdapter) :
    ListAdapter<DataMainEntity, RecyclerView.ViewHolder>(DiffCallback) {

    interface CallBackAdapter {
        fun passingData(id: String)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DataMainEntity>() {
        override fun areItemsTheSame(oldItem: DataMainEntity, newItem: DataMainEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataMainEntity, newItem: DataMainEntity): Boolean {
            return oldItem.imgSrc == newItem.imgSrc
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sfwHolder = holder as StaggeredImagesViewHolder
        sfwHolder.bind(getItem(position), onClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StaggeredImagesViewHolder(
            StaggeredViewItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

}