package com.portfolio.tasky

import android.os.Bundle
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    private var mActivity: BaseActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as BaseActivity
    }

    fun inflateFragment(fragment: BaseFragment) {
        mActivity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, fragment, javaClass.name)?.commitAllowingStateLoss()
    }

}