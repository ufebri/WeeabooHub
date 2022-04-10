package com.raytalktech.weeaboohub.ui.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raytalktech.weeaboohub.R
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.raytalktech.weeaboohub.databinding.GridViewItemBinding
import com.raytalktech.weeaboohub.ui.adapter.PhotoGridAdapter

class GridImagesViewHolder(private var binding: GridViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: DataMainEntity, onClick: PhotoGridAdapter.CallBackAdapter) {

        Glide.with(binding.root)
            .load(data.imgSrc)
            .placeholder(
                ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.loading_animation
                )
            )
            .into(binding.marsImage)

        binding.root.setOnClickListener { onClick.passingData(data.imgSrc) }
    }
}