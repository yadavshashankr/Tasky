package com.portfolio.tasky.usecases

import android.view.animation.TranslateAnimation
import androidx.appcompat.widget.Toolbar
import com.portfolio.tasky.globals.Constants


class ToolbarHandlerImpl : ToolbarHandler {
    private lateinit var translateAnimation: TranslateAnimation

    override fun setToolBarText(toolbar: Toolbar, text: String) {
        toolbar.title = text
        animateDown(toolbar)
    }

     private fun animateDown(toolbar: Toolbar) {
        translateAnimation = TranslateAnimation(0f, 0f, Constants.Companion.ToolbarAnimationProperties.MAX_TRANSLATION_Y, 0f)
        translateAnimation.duration = Constants.Companion.ToolbarAnimationProperties.DURATION
        toolbar.startAnimation(translateAnimation)
    }
}