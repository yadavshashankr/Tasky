package com.portfolio.tasky.entry


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.portfolio.tasky.*
import com.portfolio.tasky.databinding.ActivityEntryBinding
import com.portfolio.tasky.entry.fragments.LoginFragment
import com.portfolio.tasky.globals.Constants
import com.portfolio.tasky.usecases.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryActivity : AppCompatActivity(), FragmentInflater by FragmentInflaterImpl(), ToolbarHandler by ToolbarHandlerImpl() {
    private lateinit var viewBinding: ActivityEntryBinding
    private lateinit var networkChangeReceiver : NetworkChangeReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_entry)

        setObservers()
        startLoginFragment()
    }

    private fun setObservers() {
        networkChangeReceiver = NetworkChangeReceiver(this)
        networkChangeReceiver.observe(this){
            when(it){
                NetworkStatus.Available -> networkAvailability(true)
                NetworkStatus.Unavailable -> networkAvailability(false)
            }
        }
    }

    private fun networkAvailability(isAvailable : Boolean){
        onConnected(viewBinding.layoutOnlineMode.tvOnlineMode, isAvailable)
        if(isAvailable){
            viewBinding.layoutOnlineMode.drawableFirst = ContextCompat.getDrawable(this, R.drawable.ic_online)
        }else{
            viewBinding.layoutOnlineMode.drawableFirst = ContextCompat.getDrawable(this, R.drawable.ic_offline)
        }
    }

     private fun onConnected(offlineTextField: AppCompatTextView, connected: Boolean) {
        if (connected){
            offlineTextField.text = Constants.Companion.AppConnectivityMode.ONLINE
        }else{
            offlineTextField.text = Constants.Companion.AppConnectivityMode.OFFLINE
        }
        animate(offlineTextField, connected)
    }

    private fun animate(offlineTextField: AppCompatTextView, connected: Boolean) {
        offlineTextField.visibility = View.VISIBLE
        val endToStartAnimation = TranslateAnimation(500f, 0f, 0f, 0f)
        endToStartAnimation.duration = Constants.Companion.AnimationProperties.DURATION
        offlineTextField.startAnimation(endToStartAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            offlineTextField.text = ""
            if (connected){
                offlineTextField.visibility =  View.GONE
            }
        }, 3000)
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
}