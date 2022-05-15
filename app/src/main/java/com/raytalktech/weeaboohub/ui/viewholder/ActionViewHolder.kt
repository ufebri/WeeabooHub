package com.raytalktech.weeaboohub.ui.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.raytalktech.weeaboohub.databinding.ItemActionLinearBinding
import com.raytalktech.weeaboohub.ui.adapter.ActionLinearAdapter

class ActionViewHolder(private var binding: ItemActionLinearBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        mListTitle: String,
        mListIcon: Int,
        onClick: ActionLinearAdapter.CallBackAdapter
    ) {

        binding.ivActionSrc.setImageDrawable(
            ContextCompat.getDrawable(
                binding.root.context,
                mListIcon
            )
        )

        binding.tvActionName.text = mListTitle

        binding.root.setOnClickListener { onClick.actionListener(mListTitle) }
    }
}