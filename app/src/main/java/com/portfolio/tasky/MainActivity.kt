package com.portfolio.tasky


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.TranslateAnimation
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.portfolio.tasky.databinding.ActivityEntryBinding
import com.portfolio.tasky.entry.fragments.LoginFragment
import com.portfolio.tasky.entry.fragments.RegistrationFragment
import com.portfolio.tasky.entry.viewModels.EntryViewModel
import com.portfolio.tasky.globals.Constants
import com.portfolio.tasky.usecases.*
import com.portfolio.tasky.usecases.domain.FragmentInflater
import com.portfolio.tasky.usecases.domain.ToolbarHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentInflater by FragmentInflaterImpl(), ToolbarHandler by ToolbarHandlerImpl(),  OnClickListener {
    private lateinit var viewBinding: ActivityEntryBinding
    private val viewModel: EntryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_entry)
        viewBinding.lifecycleOwner = this

        setObservers()
        startFragment(RegistrationFragment.getInstance())
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

    fun setFabLocation(shiftRight : Boolean){
        if(shiftRight){
            viewBinding.fab.updateLayoutParams<ConstraintLayout.LayoutParams> {
                this.startToStart = ConstraintLayout.LayoutParams.UNSET
                this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
        }else{
            viewBinding.fab.updateLayoutParams<ConstraintLayout.LayoutParams> {
                this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                this.endToEnd = ConstraintLayout.LayoutParams.UNSET
            }
        }
    }

    fun showFAB(iconRes : Int, tag : String){
        viewBinding.fab.setImageResource(iconRes)
        viewBinding.fab.animate().translationY(0f)
        viewBinding.fab.tag = tag

        viewBinding.fab.setOnClickListener(this)
    }

    fun hideFAB(){
        viewBinding.fab.animate().translationY(resources.getDimension(com.intuit.sdp.R.dimen._200sdp))
        viewBinding.fab.setOnClickListener(null)
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

    fun startFragment(fragment : Fragment) {
        setFragmentManager(supportFragmentManager)
        setTitle(getString(R.string.welcome_back))

        inflateFragment(fragment, R.id.fragment_container)
    }

    fun setTitle(text: String) {
        setToolBarText(viewBinding.toolbar, text)
    }

    fun setToolbarHeight(isBig: Boolean) {
        setToolBarHeight(viewBinding.toolbar, viewBinding.appBar, this, isBig)
    }

    override fun onClick(view: View?) {
        if(view == viewBinding.fab && viewBinding.fab.tag == "viewTag"){
            startFragment(LoginFragment.getInstance())
        }
    }
}