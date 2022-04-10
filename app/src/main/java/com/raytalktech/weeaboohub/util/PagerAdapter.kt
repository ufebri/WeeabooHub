package com.raytalktech.weeaboohub.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created by Uray Febri on 07/03/2022.
 */
class PagerAdapter(
    fragmentManager: FragmentManager,
    private val titles: List<CharSequence>,
    private val content: List<Fragment>
) : FragmentStatePagerAdapter(fragmentManager) {

    private val numOfTabs: Int = titles.size

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            position -> content[position]
            else -> content[0]
        }

        return fragment
    }

    override fun getCount(): Int {
        return numOfTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}