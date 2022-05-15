package com.raytalktech.weeaboohub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.raytalktech.weeaboohub.databinding.GridViewItemBinding
import com.raytalktech.weeaboohub.ui.viewholder.GridImagesViewHolder

class PhotoGridAdapter(private val onClick: CallBackAdapter) :
    ListAdapter<DataMainEntity, GridImagesViewHolder>(DiffCallback) {

    interface CallBackAdapter {
        fun passingData(id: String)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DataMainEntity>() {
        override fun areItemsTheSame(
            oldItem: DataMainEntity,
            newItem: DataMainEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataMainEntity,
            newItem: DataMainEntity
        ): Boolean {
            return oldItem.imgSrc == newItem.imgSrc
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GridImagesViewHolder {
        return GridImagesViewHolder(
            GridViewItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: GridImagesViewHolder, position: Int) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto, onClick)
    }
}