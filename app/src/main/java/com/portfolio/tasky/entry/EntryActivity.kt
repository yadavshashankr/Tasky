package com.portfolio.tasky.entry


import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.portfolio.tasky.*
import com.portfolio.tasky.databinding.ActivityEntryBinding
import com.portfolio.tasky.entry.fragments.LoginFragment
import com.portfolio.tasky.usecases.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryActivity : AppCompatActivity(), FragmentInflater by FragmentInflaterImpl(), ToolbarHandler by ToolbarHandlerImpl(), OfflineModeHandler by OfflineModeHandlerImpl(), InternetStatus {
    private lateinit var viewBinding: ActivityEntryBinding
    private lateinit var networkChangeReceiver: NetworkChangeReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_entry)

        startLoginFragment()
    }

    private fun setNetworkAvailabilityListeners() {
        networkChangeReceiver = NetworkChangeReceiver(this)
        registerReceiver(networkChangeReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }

    private fun unregisterListeners(){
        unregisterReceiver(networkChangeReceiver)
    }

    private fun startLoginFragment() {
        setTitle(getString(R.string.welcome_back))

        setFragmentManager(supportFragmentManager)
        val loginFragment = LoginFragment.getInstance()
        inflateFragment(loginFragment, R.id.fragment_container)
    }

    fun setTitle(text: String) {
        setToolBarText(viewBinding.toolbar, text)
    }

    override fun internetAvailable() {
        viewBinding.layoutOnlineMode.tvOnlineMode.let { onConnected(it, true) }
        viewBinding.layoutOnlineMode.drawableFirst = ContextCompat.getDrawable(this, R.drawable.ic_online)
    }

    override fun internetNotAvailable() {
        viewBinding.layoutOnlineMode.tvOnlineMode.let { onConnected(it, false) }
        viewBinding.layoutOnlineMode.drawableFirst = ContextCompat.getDrawable(this, R.drawable.ic_offline)
    }

    override fun onResume() {
        super.onResume()
        setNetworkAvailabilityListeners()
    }

    override fun onStop() {
        super.onStop()
        unregisterListeners()
    }
}