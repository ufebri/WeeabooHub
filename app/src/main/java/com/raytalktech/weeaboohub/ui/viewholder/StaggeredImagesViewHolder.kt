package com.raytalktech.weeaboohub.ui.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.raytalktech.weeaboohub.R
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.raytalktech.weeaboohub.databinding.StaggeredViewItemBinding
import com.raytalktech.weeaboohub.ui.adapter.PhotoStaggeredAdapter

class StaggeredImagesViewHolder(private var binding: StaggeredViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: DataMainEntity, onClick: PhotoStaggeredAdapter.CallBackAdapter) {

        Glide.with(binding.root)
            .load(data.imgSrc)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(
                ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.loading_animation
                )
            ).into(binding.ivItem)

        binding.root.setOnClickListener { onClick.passingData(data.id) }
    }
}