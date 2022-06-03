package com.raytalktech.weeaboohub.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.raytalktech.weeaboohub.config.Constant
import com.raytalktech.weeaboohub.data.source.local.entity.DataMainEntity
import com.raytalktech.weeaboohub.databinding.ContentFragmentBookmarkBinding
import com.raytalktech.weeaboohub.databinding.GeneralContainerViewBinding
import com.raytalktech.weeaboohub.ui.adapter.PhotoStaggeredAdapter
import com.raytalktech.weeaboohub.ui.detail.DetailBottomSheet
import com.raytalktech.weeaboohub.util.GeneralInfoUtil
import com.raytalktech.weeaboohub.util.ViewModelFactory

class BookmarkFragment : Fragment() {

    private var bookmarkBinding: ContentFragmentBookmarkBinding? = null
    private var containerBinding: GeneralContainerViewBinding? = null
    private val binding get() = bookmarkBinding
    private lateinit var viewModel: BookmarkViewModel
    private var mData: List<DataMainEntity> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookmarkBinding = ContentFragmentBookmarkBinding.inflate(inflater, container, false).also {
            containerBinding = GeneralContainerViewBinding.inflate(inflater, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[BookmarkViewModel::class.java]

        viewModel.getBookmarkList().observe(viewLifecycleOwner, { result ->
            if (result.isNotEmpty()) {
                mData = result
                photoStaggeredAdapter.submitList(mData)
                isRvVisible(true)
            } else {
                isRvVisible(false)
                setContainerData(Constant.RESPONSE_CODE.NO_DATA)
            }
        })
    }


    private fun setContainerData(RC: Constant.RESPONSE_CODE) {
        binding?.clContainer?.apply {
            ivItem.setImageDrawable(GeneralInfoUtil(requireContext(), RC).getDrawableImage)
            tvItem.text = GeneralInfoUtil(requireContext(), RC).getMessage
            btnAction.text = GeneralInfoUtil(requireContext(), RC).getButtonTextAction
            root.isVisible = true
        }
    }

    private fun isRvVisible(isShow: Boolean) {
        binding?.rvBookmark?.apply {
            adapter = photoStaggeredAdapter
            layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            isVisible = isShow
        }
    }

    private val photoStaggeredAdapter: PhotoStaggeredAdapter by lazy {
        PhotoStaggeredAdapter(object : PhotoStaggeredAdapter.CallBackAdapter {
            override fun passingData(id: String) {
                DetailBottomSheet.newInstance(id)
                    .show(childFragmentManager, DetailBottomSheet::class.java.canonicalName)
            }
        }).apply { }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bookmarkBinding = null
    }
}