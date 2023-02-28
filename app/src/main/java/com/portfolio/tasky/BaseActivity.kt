package com.portfolio.tasky

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    private var mActivity: BaseActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // Not implemented yet
    }

    override fun onStart() {
        super.onStart()
        // Not implemented yet
    }

    override fun onPause() {
        super.onPause()
        // Not implemented yet
    }

    override fun onStop() {
        super.onStop()
        // Not implemented yet
    }

    override fun onResume() {
        super.onResume()
        // Not implemented yet
    }

    override fun onDestroy() {
        super.onDestroy()
        // Not implemented yet
    }

    fun launchActivity(cls: Class<*>?) {
        val privateStorageIntent = Intent(mActivity, cls)
        startActivity(privateStorageIntent)
    }

}