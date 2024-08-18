package com.raytalktech.weeaboohub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.raytalktech.weeaboohub.R
import com.raytalktech.weeaboohub.config.Constant
import com.raytalktech.weeaboohub.databinding.FragmentHomeBinding
import com.raytalktech.weeaboohub.util.PagerAdapter
import com.raytalktech.weeaboohub.util.UtilHelper.getInlineAdaptiveAdSize

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private var adView: AdView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val mListFragmentItem: ArrayList<Fragment> = arrayListOf()
            for (item in Constant.listCategory.indices)
                mListFragmentItem.add(HomeFragmentItem.newInstance(Constant.listCategory[item]))

            binding?.apply {
                val fragmentAdapter = PagerAdapter(
                    childFragmentManager,
                    Constant.listCategory,
                    mListFragmentItem
                )
                viewpagerMain.adapter = fragmentAdapter
                tabsMain.setupWithViewPager(viewpagerMain)

                val adView = AdView(requireActivity())
                adView.adUnitId = getString(R.string.admob_banner_id)
                // Get the adaptive AdSize
                val adSize = getInlineAdaptiveAdSize()
                adSize?.let { adView.setAdSize(it) }
                this@HomeFragment.adView = adView

                adViewB.removeAllViews()
                adViewB.addView(adView)

                val adRequest = AdRequest.Builder().build()
                adView.loadAd(adRequest)
            }
        }
    }

    override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        adView?.resume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}