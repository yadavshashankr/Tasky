package com.portfolio.tasky.entry


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.portfolio.tasky.*
import com.portfolio.tasky.databinding.ActivityEntryBinding
import com.portfolio.tasky.entry.fragments.LoginFragment
import com.portfolio.tasky.usecases.FragmentInflater
import com.portfolio.tasky.usecases.FragmentInflaterImpl
import com.portfolio.tasky.usecases.ToolbarHandler
import com.portfolio.tasky.usecases.ToolbarHandlerImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryActivity : AppCompatActivity(), FragmentInflater by FragmentInflaterImpl(), ToolbarHandler by ToolbarHandlerImpl() {
    private var viewBinding: ActivityEntryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_entry)

        startLoginFragment()
    }

    private fun startLoginFragment() {
        setTitle(getString(R.string.welcome_back))

        setFragmentManager(supportFragmentManager)
        val loginFragment = LoginFragment.getInstance()
        inflateFragment(loginFragment, R.id.fragment_container)
    }

    fun setTitle(text: String) {
        setToolBarText(viewBinding?.toolbar as Toolbar, text)
    }
}