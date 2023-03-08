package com.portfolio.tasky.usecases.domain

import android.text.Editable
import com.portfolio.tasky.views.TaskyAppCompatEditText

interface TextChanged {
    fun onTextChanged(editable: Editable, taskyAppcompatEditText: TaskyAppCompatEditText)
}