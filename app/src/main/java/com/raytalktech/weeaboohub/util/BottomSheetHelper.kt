package com.raytalktech.weeaboohub.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raytalktech.weeaboohub.R
import com.raytalktech.weeaboohub.config.Constant
import com.raytalktech.weeaboohub.databinding.BottomSheetBinding
import com.raytalktech.weeaboohub.ui.adapter.ActionLinearAdapter

class BottomSheetHelper : BottomSheetDialogFragment() {

    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding
    private var url = ""

    companion object {
        private const val ARG_TEXT = "fragmentTAG"
        fun newInstance(url: String) = BottomSheetHelper().apply {
            arguments = Bundle().apply { putString(ARG_TEXT, url) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { url = it.getString(ARG_TEXT) ?: "" }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            binding?.apply {
                Glide.with(requireContext())
                    .load(url)
                    .placeholder(R.drawable.loading_animation)
                    .into(ivItem)

                rvAction.adapter = actionLinearAdapter
                rvAction.layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                rvAction.setHasFixedSize(true)
            }

            initDialog()
        }
    }

    private fun initDialog() {
        requireDialog().window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        requireDialog().window?.statusBarColor =
            getColor(requireContext(), android.R.color.transparent)
    }

    private val actionLinearAdapter: ActionLinearAdapter by lazy {
        ActionLinearAdapter(object : ActionLinearAdapter.CallBackAdapter {
            override fun actionListener(action: String) {
                actionClickListener(action)
            }
        }).apply {}
    }

    private fun actionClickListener(action: String) {
        when (action) {
            Constant.listActionAdapter[0] -> {}
            Constant.listActionAdapter[1] -> {}
            Constant.listActionAdapter[2] -> {}
            Constant.listActionAdapter[3] -> {}
        }
    }
}