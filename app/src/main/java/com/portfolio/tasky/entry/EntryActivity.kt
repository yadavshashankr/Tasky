package com.portfolio.tasky.entry


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.TranslateAnimation
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.portfolio.tasky.*
import com.portfolio.tasky.databinding.ActivityEntryBinding
import com.portfolio.tasky.entry.fragments.LoginFragment
import com.portfolio.tasky.entry.viewModels.EntryViewModel
import com.portfolio.tasky.globals.Constants
import com.portfolio.tasky.usecases.*
import com.portfolio.tasky.usecases.domain.FragmentInflater
import com.portfolio.tasky.usecases.domain.ToolbarHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryActivity : AppCompatActivity(), FragmentInflater by FragmentInflaterImpl(), ToolbarHandler by ToolbarHandlerImpl() {
    private lateinit var viewBinding: ActivityEntryBinding
    private val viewModel: EntryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_entry)
        viewBinding.lifecycleOwner = this

        setObservers()
        startLoginFragment()
    }

    private fun setObservers() {
        viewModel.networkStatusObserver.observe(this){
                when(it){
                NetworkStatus.Available -> {
                    viewBinding.layoutOnlineMode.drawableFirst = ContextCompat.getDrawable(this, R.drawable.ic_online)
                    viewBinding.layoutOnlineMode.tvOnlineMode.text = getString(R.string.text_online)
                    animate(true)
                }
                NetworkStatus.Unavailable -> {
                    viewBinding.layoutOnlineMode.drawableFirst = ContextCompat.getDrawable(this, R.drawable.ic_offline)
                    viewBinding.layoutOnlineMode.tvOnlineMode.text = getString(R.string.text_offline)
                    animate(false)
                }
            }
        }
    }

    private fun animate(isAvailable: Boolean) {
        val textField = viewBinding.layoutOnlineMode.tvOnlineMode
        textField.isVisible = true
        val endToStartAnimation = TranslateAnimation(500f, 0f, 0f, 0f)
        endToStartAnimation.duration = Constants.Companion.AnimationProperties.DURATION
        textField.startAnimation(endToStartAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            viewBinding.layoutOnlineMode.tvOnlineMode.text = ""
            if (isAvailable){
                textField.isVisible = false
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