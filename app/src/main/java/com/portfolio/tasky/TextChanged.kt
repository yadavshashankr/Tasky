package com.portfolio.tasky

import android.text.Editable
import com.portfolio.tasky.views.TaskyAppCompatEditText

interface TextChanged {
    fun onTextChanged(editable: Editable, taskyAppcompatEditText: TaskyAppCompatEditText)
}