package com.portfolio.tasky


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.PopupWindow
import android.widget.Toast
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
import com.portfolio.tasky.usecases.FragmentInflaterImpl
import com.portfolio.tasky.usecases.NetworkStatus
import com.portfolio.tasky.usecases.ToolbarHandlerImpl
import com.portfolio.tasky.usecases.domain.FragmentInflater
import com.portfolio.tasky.usecases.domain.ToolbarHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentInflater by FragmentInflaterImpl(), ToolbarHandler by ToolbarHandlerImpl(),  OnClickListener {
    private lateinit var viewBinding: ActivityEntryBinding
    private val viewModel: EntryViewModel by viewModels()
    private lateinit var popUpActionWindow: PopupWindow
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

        viewModel.agendaDialogObserver().observe(this) {
            when (it) {
                //Agenda Type
                getString(R.string.task) -> Toast.makeText(this, getString(R.string.task), Toast.LENGTH_SHORT).show()
                getString(R.string.event) -> Toast.makeText(this, getString(R.string.event), Toast.LENGTH_SHORT).show()
                getString(R.string.reminder) -> Toast.makeText(this, getString(R.string.reminder), Toast.LENGTH_SHORT).show()

                //Agenda State
                getString(R.string.open) -> Toast.makeText(this, getString(R.string.open), Toast.LENGTH_SHORT).show()
                getString(R.string.edit) -> Toast.makeText(this, getString(R.string.edit), Toast.LENGTH_SHORT).show()
                getString(R.string.delete) -> Toast.makeText(this, getString(R.string.delete), Toast.LENGTH_SHORT).show()

                //Agenda Pre Timers
                getString(R.string.ten_mins_before) -> Toast.makeText(this, getString(R.string.ten_mins_before), Toast.LENGTH_SHORT).show()
                getString(R.string.thirty_mins_before) -> Toast.makeText(this, getString(R.string.thirty_mins_before), Toast.LENGTH_SHORT).show()
                getString(R.string.one_hour_before) -> Toast.makeText(this, getString(R.string.one_hour_before), Toast.LENGTH_SHORT).show()
                getString(R.string.six_hours_before) -> Toast.makeText(this, getString(R.string.six_hours_before), Toast.LENGTH_SHORT).show()
                getString(R.string.one_day_before) -> Toast.makeText(this, getString(R.string.one_day_before), Toast.LENGTH_SHORT).show()

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

    private fun showAgendaPopUp(agenda : Constants.Companion.Agenda){
        val calculatedHeight: Int
        val halfScreenWidth: Int
        val calculatedWidth: Int

        if(agenda is Constants.Companion.AgendaPreTimeParams){
            calculatedWidth = viewBinding.fab.x.toInt() - resources.getDimension(com.intuit.sdp.R.dimen._165sdp).toInt()
            calculatedHeight = viewBinding.fab.y.toInt() - resources.getDimension(com.intuit.sdp.R.dimen._255sdp).toInt()
            halfScreenWidth = resources.displayMetrics.widthPixels / 2 + 60
        }else{
            calculatedWidth = viewBinding.fab.x.toInt() - resources.getDimension(com.intuit.sdp.R.dimen._135sdp).toInt()
            calculatedHeight = viewBinding.fab.y.toInt() - resources.getDimension(com.intuit.sdp.R.dimen._135sdp).toInt()
            halfScreenWidth = resources.displayMetrics.widthPixels / 2 - 30
        }

        popUpActionWindow = viewModel.showAgendaDialog(agenda)
        popUpActionWindow.showAsDropDown(viewBinding.fab, 0, 0, Gravity.END)
        popUpActionWindow.update(calculatedWidth, calculatedHeight, halfScreenWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onClick(view: View?) {
        if(view == viewBinding.fab && viewBinding.fab.tag == "fab"){
            startFragment(LoginFragment.getInstance())
        }
        if(view == viewBinding.fab && viewBinding.fab.tag == "agendaDialog"){
            showAgendaPopUp(Constants.Companion.AgendaType)
        }
    }
}