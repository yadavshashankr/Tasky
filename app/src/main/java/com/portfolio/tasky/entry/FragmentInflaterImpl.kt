package com.portfolio.tasky.entry

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.portfolio.tasky.R

class FragmentInflaterImpl : FragmentInflater {
    private lateinit var fragmentManager: FragmentManager

    override fun setFragmentManager(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    override fun inflateFragment(fragment: Fragment, viewID: Int) {
        fragmentManager.beginTransaction().replace(viewID, fragment, javaClass.name).commitAllowingStateLoss()
    }
}