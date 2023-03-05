package com.portfolio.tasky.usecases

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.widget.AppCompatTextView
import com.portfolio.tasky.globals.Constants

class OfflineModeHandlerImpl : OfflineModeHandler {
    private lateinit var translateAnimation: TranslateAnimation

    override fun onConnected(offlineTextField: AppCompatTextView, connected: Boolean) {

        offlineTextField.visibility = View.VISIBLE

        if (connected){
            offlineTextField.text = Constants.Companion.AppConnectivityMode.ONLINE
        }else{
            offlineTextField.text = Constants.Companion.AppConnectivityMode.OFFLINE
        }

        animate(offlineTextField, connected)
    }

    private fun animate(offlineTextField: AppCompatTextView, connected: Boolean) {

        translateAnimation = TranslateAnimation(500f, 0f, 0f, 0f)
        translateAnimation.duration = Constants.Companion.ToolbarAnimationProperties.DURATION
        offlineTextField.startAnimation(translateAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            offlineTextField.text = ""
            if (connected){
                offlineTextField.visibility =  View.GONE
            }
        }, 3000)

    }
}