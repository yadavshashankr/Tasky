package com.portfolio.tasky.entry


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.portfolio.tasky.R
import com.portfolio.tasky.entry.fragments.LoginFragment

class EntryActivity : AppCompatActivity(), FragmentInflater by FragmentInflaterImpl() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        startLoginFragment()
    }

    private fun startLoginFragment() {
        setFragmentManager(supportFragmentManager)
        val loginFragment = LoginFragment.getInstance()
        inflateFragment(loginFragment, R.id.fragment_container)
    }
}