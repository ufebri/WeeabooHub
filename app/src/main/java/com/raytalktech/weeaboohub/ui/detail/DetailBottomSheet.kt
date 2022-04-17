package com.raytalktech.weeaboohub.ui.detail

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raytalktech.weeaboohub.R
import com.raytalktech.weeaboohub.config.Constant
import com.raytalktech.weeaboohub.databinding.BottomSheetBinding
import com.raytalktech.weeaboohub.ui.adapter.ActionLinearAdapter
import com.raytalktech.weeaboohub.util.GeneralHelper
import com.raytalktech.weeaboohub.util.ViewModelFactory

class DetailBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding
    private var id = ""
    private lateinit var viewModel: DetailViewModel

    companion object {
        private const val ARG_TEXT = "fragmentTAG"
        fun newInstance(id: String) = DetailBottomSheet().apply {
            arguments = Bundle().apply { putString(ARG_TEXT, id) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { id = it.getString(ARG_TEXT) ?: "" }
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

            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

            viewModel.selectedID(id)
            viewModel.getDataByID.observe(this, { result -> populateData(result.imgSrc) })
            initDialog()
        }
    }

    private fun populateData(url: String) {
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
    }

    private fun initDialog() {
        requireDialog().window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        requireDialog().window?.statusBarColor =
            getColor(requireContext(), android.R.color.transparent)
    }

    private val actionLinearAdapter: ActionLinearAdapter by lazy {
        ActionLinearAdapter(object : ActionLinearAdapter.CallBackAdapter {
            override fun actionListener(action: String) {
                viewModel.selectedAction(action, requireContext())
                viewModel.actionOperations()
            }
        }).apply {}
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            when (requestCode) {
                Constant.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> viewModel.actionOperations()
            }
        } else {
            GeneralHelper.showToastMessage(requireContext(), "")
        }
    }
}