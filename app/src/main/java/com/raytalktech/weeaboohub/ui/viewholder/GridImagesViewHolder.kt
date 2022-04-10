package com.raytalktech.weeaboohub.ui.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raytalktech.weeaboohub.R
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.raytalktech.weeaboohub.databinding.GridViewItemBinding

class GridImagesViewHolder(private var binding: GridViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: DataMainEntity) {

        Glide.with(binding.root)
            .load(data.imgSrc)
            .placeholder(
                ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.loading_animation
                )
            )
            .into(binding.marsImage)
    }
}