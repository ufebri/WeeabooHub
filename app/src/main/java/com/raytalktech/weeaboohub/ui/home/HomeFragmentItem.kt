package com.raytalktech.weeaboohub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.raytalktech.weeaboohub.R
import com.raytalktech.weeaboohub.config.Constant
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.raytalktech.weeaboohub.databinding.ContentFragmentHomeBinding
import com.raytalktech.weeaboohub.ui.detail.DetailBottomSheet
import com.raytalktech.weeaboohub.util.RemappingData.remappingResponseData
import com.raytalktech.weeaboohub.util.ViewModelFactory
import com.raytalktech.weeaboohub.util.vo.Resource
import com.raytalktech.weeaboohub.util.vo.Status
import com.ufebri.androidbaseprime.domain.model.ItemData
import com.ufebri.androidbaseprime.ui.adapter.PhotoGridAdapter
import com.ufebri.androidbaseprime.util.GeneralHelper.showToastMessage
import com.ufebri.androidbaseprime.util.OnClickListener

class HomeFragmentItem : Fragment() {

    private var _binding: ContentFragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: HomeViewModel
    private var mData: List<DataMainEntity> = emptyList()
    private var text = ""

    companion object {
        private const val ARG_TEXT = "fragmentTAG"

        fun newInstance(text: String) =
            HomeFragmentItem().apply {
                arguments =
                    Bundle().apply {
                        putString(ARG_TEXT, text)
                    }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            text = it.getString(ARG_TEXT) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ContentFragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

            if (Constant.listCategory.contains(text))
                viewModel.getAllDataList(Constant.listType[0], text)
                    .observe(viewLifecycleOwner, populateListData)

            binding?.photosGrid?.let {
                with(it) {
                    adapter = photoGridAdapter
                    layoutManager = GridLayoutManager(context, 2)
                    setHasFixedSize(true)
                }
            }
        }
    }

    private val populateListData = Observer<Resource<List<DataMainEntity>>> { result ->
        when (result.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> if (result.data != null) {
                mData = result.data
                photoGridAdapter.submitList(remappingResponseData(mData))
            }

            Status.ERROR -> showToastMessage(
                requireContext(),
                result?.message.toString()
            )
        }
    }

    private val photoGridAdapter: PhotoGridAdapter by lazy {
        PhotoGridAdapter(object : OnClickListener {
            override fun onClickItem(item: ItemData) {
                DetailBottomSheet.newInstance(item.field1s ?: "")
                    .show(childFragmentManager, DetailBottomSheet::class.java.canonicalName)
            }
        }, getString(R.string.admob_native_id))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}