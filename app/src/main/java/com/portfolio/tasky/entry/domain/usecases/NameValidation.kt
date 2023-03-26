package com.portfolio.tasky.entry.domain.usecases

import javax.inject.Inject

class NameValidation @Inject constructor() {
    fun isValidName(name: CharSequence): Boolean {
        return name.isNotEmpty() && name.length > 3
    }
}