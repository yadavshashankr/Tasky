package com.portfolio.tasky.entry


import android.os.Bundle
import com.portfolio.tasky.BaseActivity
import com.portfolio.tasky.BaseFragment
import com.portfolio.tasky.R
import com.portfolio.tasky.entry.fragments.LoginFragment

class EntryActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        val loginFragment = LoginFragment.getInstance()
        inflateFragment(loginFragment)
    }

    private fun inflateFragment(fragment: BaseFragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, javaClass.name)
            .commitAllowingStateLoss()
    }
}