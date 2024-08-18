package com.raytalktech.weeaboohub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.raytalktech.weeaboohub.R
import com.raytalktech.weeaboohub.config.Constant
import com.raytalktech.weeaboohub.databinding.FragmentHomeBinding
import com.raytalktech.weeaboohub.util.GoogleMobileAdsConsentManager
import com.raytalktech.weeaboohub.util.PagerAdapter
import com.raytalktech.weeaboohub.util.UtilHelper.getInlineAdaptiveAdSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private var adView: AdView? = null
    private lateinit var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)
    private val initialLayoutComplete = AtomicBoolean(false)


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

            // Log the Mobile Ads SDK version.
            Log.d("ADS-LOG", "Google Mobile Ads SDK Version: " + MobileAds.getVersion())

            googleMobileAdsConsentManager =
                GoogleMobileAdsConsentManager.getInstance(requireContext())
            googleMobileAdsConsentManager.gatherConsent(requireActivity()) { error ->
                if (error != null) {
                    // Consent not obtained in current session.
                    Log.d("ADS-LOG", "${error.errorCode}: ${error.message}")
                }

                if (googleMobileAdsConsentManager.canRequestAds) {
                    initializeMobileAdsSdk()
                }

                if (googleMobileAdsConsentManager.isPrivacyOptionsRequired) {
                    // Regenerate the options menu to include a privacy setting.
                    requireActivity().invalidateOptionsMenu()
                }
            }

            // This sample attempts to load ads using consent obtained in the previous session.
            if (googleMobileAdsConsentManager.canRequestAds) {
                initializeMobileAdsSdk()
            }

            // Since we're loading the banner based on the adContainerView size, we need to wait until this
            // view is laid out before we can get the width.
            binding?.adViewB?.viewTreeObserver?.addOnGlobalLayoutListener {
                if (!initialLayoutComplete.getAndSet(true) && googleMobileAdsConsentManager.canRequestAds) {
                    loadBanner()
                }
            }


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
            }
        }
    }

    private fun loadBanner() {
        binding?.apply {
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

    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return
        }

        // Set your test devices.
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("ABCDEF012345")).build()
        )

        CoroutineScope(Dispatchers.IO).launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(requireActivity()) {}

            requireActivity().runOnUiThread {
                // Load an ad on the main thread.
                if (initialLayoutComplete.get()) {
                    loadBanner()
                }
            }
        }
    }
}