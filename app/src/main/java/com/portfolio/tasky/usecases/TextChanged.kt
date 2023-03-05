package com.portfolio.tasky.usecases

import android.text.Editable
import com.portfolio.tasky.views.TaskyAppCompatEditText

interface TextChanged {
    fun onTextChanged(editable: Editable, taskyAppcompatEditText: TaskyAppCompatEditText)
}