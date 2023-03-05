package com.portfolio.tasky.usecases

import androidx.appcompat.widget.AppCompatTextView

interface OfflineModeHandler {
    fun onConnected(offlineTextField: AppCompatTextView, connected : Boolean)
}