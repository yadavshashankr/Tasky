package com.portfolio.tasky.usecases

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentInflaterImpl : FragmentInflater {
    private lateinit var fragmentManager: FragmentManager

    override fun setFragmentManager(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    override fun inflateFragment(fragment: Fragment, viewID: Int) {
        fragmentManager.beginTransaction().replace(viewID, fragment, javaClass.name).commitAllowingStateLoss()
    }

    override fun removeFragment(fragment : Fragment) {
        fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
    }
}