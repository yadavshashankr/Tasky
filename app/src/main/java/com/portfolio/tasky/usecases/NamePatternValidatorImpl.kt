package com.portfolio.tasky.usecases

import javax.inject.Inject

class NamePatternValidatorImpl @Inject constructor() : NamePatternValidator {
    override fun isValidName(name: CharSequence?): Boolean {
        return Regex("^(?=.*[a-z]).{3,}\$").matches(name as CharSequence)
    }
}