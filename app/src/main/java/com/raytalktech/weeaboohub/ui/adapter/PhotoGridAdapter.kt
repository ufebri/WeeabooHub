package com.raytalktech.weeaboohub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raytalktech.weeaboohub.databinding.GridViewItemBinding
import com.raytalktech.weeaboohub.network.model.DataResponseModified

class PhotoGridAdapter :
    ListAdapter<DataResponseModified, PhotoGridAdapter.MarsPhotosViewHolder>(DiffCallback) {

    class MarsPhotosViewHolder(
        private var binding: GridViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(marsPhoto: DataResponseModified) {
            binding.photo = marsPhoto

            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DataResponseModified>() {
        override fun areItemsTheSame(
            oldItem: DataResponseModified,
            newItem: DataResponseModified
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataResponseModified,
            newItem: DataResponseModified
        ): Boolean {
            return oldItem.imgSrc == newItem.imgSrc
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarsPhotosViewHolder {
        return MarsPhotosViewHolder(
            GridViewItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MarsPhotosViewHolder, position: Int) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)
    }
}