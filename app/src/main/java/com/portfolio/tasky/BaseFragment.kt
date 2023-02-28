package com.portfolio.tasky

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    private var mActivity: BaseActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as BaseActivity
    }

    override fun onStart() {
        super.onStart()
        // Not implemented yet
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        // Not implemented yet
    }

    override fun onDestroy() {
        super.onDestroy()
        // Not implemented yet
    }

    fun inflateFragment(fragment: BaseFragment) {
        mActivity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, fragment, javaClass.name)?.commitAllowingStateLoss()
    }

}