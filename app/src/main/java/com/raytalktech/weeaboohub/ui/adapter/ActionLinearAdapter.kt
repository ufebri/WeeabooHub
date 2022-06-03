package com.raytalktech.weeaboohub.ui.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raytalktech.weeaboohub.config.Constant
import com.raytalktech.weeaboohub.databinding.ItemActionLinearBinding
import com.raytalktech.weeaboohub.ui.viewholder.ActionViewHolder

class ActionLinearAdapter(private val onClick: CallBackAdapter) :
    RecyclerView.Adapter<ActionViewHolder>() {

    private var actionList: List<String> = Constant.listActionAdapter
    private var actionIcon: List<Int> = Constant.listActionIconAdapter
    private var isFavorite: Boolean = false

    interface CallBackAdapter {
        fun actionListener(action: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val itemBinding =
            ItemActionLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActionViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.bind(actionList[position], actionIcon[position], onClick, isFavorite)
    }

    override fun getItemCount(): Int = actionList.size

    fun setFavorite(isFavorite: Boolean) {
        //TODO: is something wrong here
        this.isFavorite = isFavorite
        Handler(Looper.getMainLooper() ?: return).postDelayed({
            notifyDataSetChanged()
        }, 1)
    }
}