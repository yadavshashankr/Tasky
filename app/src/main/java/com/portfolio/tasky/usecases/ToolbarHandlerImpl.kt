package com.portfolio.tasky.usecases

import android.animation.ValueAnimator
import android.content.Context
import android.view.animation.TranslateAnimation
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.portfolio.tasky.globals.Constants
import com.portfolio.tasky.usecases.domain.ToolbarHandler


class ToolbarHandlerImpl : ToolbarHandler {
    private lateinit var translateAnimation: TranslateAnimation

    override fun setToolBarText(toolbar: Toolbar, text: String) {
        toolbar.title = text
        animateDown(toolbar)
    }

    override fun setToolBarHeight(toolbar: Toolbar, appBar: AppBarLayout, context: Context, isBig: Boolean) {
        if(isBig){
            val va = ValueAnimator.ofInt(context.resources.getDimension(com.intuit.sdp.R.dimen._60sdp).toInt(), context.resources.getDimension(com.intuit.sdp.R.dimen._120sdp).toInt())
            va.duration = Constants.Companion.AnimationProperties.DURATION
            va.addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                toolbar.layoutParams.height = value
                toolbar.requestLayout()
                appBar.layoutParams.height = value
                appBar.requestLayout()
            }
            va.start()
        }else{
            val va = ValueAnimator.ofInt(context.resources.getDimension(com.intuit.sdp.R.dimen._120sdp).toInt(), context.resources.getDimension(com.intuit.sdp.R.dimen._60sdp).toInt())
            va.duration = Constants.Companion.AnimationProperties.DURATION
            va.addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                toolbar.layoutParams.height = value
                toolbar.requestLayout()
                appBar.layoutParams.height = value
                appBar.requestLayout()
            }
            va.start()
        }
    }

    private fun animateDown(toolbar: Toolbar) {
        translateAnimation = TranslateAnimation(0f, 0f, Constants.Companion.AnimationProperties.MAX_TRANSLATION_Y, 0f)
        translateAnimation.duration = Constants.Companion.AnimationProperties.DURATION
        toolbar.startAnimation(translateAnimation)
    }
}