package com.raytalktech.weeaboohub.ui.detail

import android.app.AlertDialog
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
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.raytalktech.weeaboohub.databinding.BottomSheetBinding
import com.raytalktech.weeaboohub.ui.adapter.ActionLinearAdapter
import com.raytalktech.weeaboohub.util.*
import com.raytalktech.weeaboohub.util.GeneralHelper.showSnackBar

class DetailBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding
    private var id = ""
    private lateinit var viewModel: DetailViewModel
    private lateinit var urlImage: String
    private lateinit var fileName: String
    private lateinit var format: String
    private lateinit var permissionManager: PermissionManager
    private lateinit var dialog: AlertDialog
    private lateinit var dialogshare: AlertDialog

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
            viewModel.getDataByID.observe(this, { result -> populateData(result) })
            permissionManager = PermissionManager.from(this)

            dialog = GeneralHelper.setProgressDialog(requireContext(), "Downloading...")
            dialogshare = GeneralHelper.setProgressDialog(requireContext(), "Loading...")

            initDialog()
        }
    }

    private fun populateData(mData: DataMainEntity) {
        urlImage = mData.imgSrc
        fileName = String.format(
            "%s_%s",
            getString(R.string.app_name),
            GeneralHelper.generateFileName(urlImage)
        )
        format = mData.format

        binding?.apply {
            Glide.with(requireContext())
                .load(urlImage)
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
                when (action) {
                    Constant.listActionAdapter[0] -> goDownload()
                    Constant.listActionAdapter[1] -> goShare()
                }
            }
        }).apply {}
    }

    private fun goDownload() {
        permissionManager.request(Permission.Storage)
            .rationale(getString(R.string.dialog_permission_save_image))
            .checkPermission { granted: Boolean ->
                if (granted) {
                    dialog.show()
                    val downloadImage = DownloadImage(requireContext())
                    downloadImage.go(fileName, urlImage, format)
                    downloadImage.getResult { isSuccess: Boolean ->
                        if (isSuccess) {
                            dialog.cancel()
                            showSnackBar("Success Saving")
                        } else {
                            dialog.cancel()
                            showSnackBar(getString(R.string.error_gesi02))
                        }
                    }
                } else {
                    GeneralHelper.showAlertDialog(
                        requireContext(),
                        getString(R.string.dialog_permission_title),
                        getString(R.string.dialog_permission_denied_permission),
                        getString(R.string.dialog_permission_button_positive)
                    )
                }
            }
    }

    private fun goShare() {
        dialogshare.show()
        val shareIntent = ShareIntent(requireContext())
        shareIntent.go(
            getString(R.string.intent_message),
            getString(R.string.intent_title),
            urlImage
        )
        shareIntent.getResult {
            isSuccess: Boolean ->
            if (isSuccess) {
                dialogshare.cancel()
            }
            else {
                dialogshare.cancel()
                showSnackBar(getString(R.string.error_gesi01))
            }
        }
    }
}