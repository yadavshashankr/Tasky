package com.portfolio.tasky.usecases.domain

import androidx.appcompat.widget.Toolbar


interface ToolbarHandler {
    fun setToolBarText(toolbar: Toolbar, text: String)
}