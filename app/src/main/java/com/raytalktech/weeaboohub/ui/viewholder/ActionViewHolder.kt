package com.raytalktech.weeaboohub.ui.viewholder

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.raytalktech.weeaboohub.R
import com.raytalktech.weeaboohub.databinding.ItemActionLinearBinding
import com.raytalktech.weeaboohub.ui.adapter.ActionLinearAdapter

class ActionViewHolder(private var binding: ItemActionLinearBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        mListTitle: String,
        mListIcon: Int,
        onClick: ActionLinearAdapter.CallBackAdapter,
        isFavorite: Boolean
    ) {

        val getDrawable: Drawable? = when (adapterPosition == 2 && isFavorite) {
            true -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_bookmarked)
            false -> ContextCompat.getDrawable(binding.root.context, mListIcon)
        }

        binding.ivActionSrc.setImageDrawable(getDrawable)

        binding.tvActionName.text = mListTitle

        binding.root.setOnClickListener { onClick.actionListener(mListTitle) }
    }
}