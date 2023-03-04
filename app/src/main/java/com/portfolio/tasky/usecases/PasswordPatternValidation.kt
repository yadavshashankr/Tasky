package com.portfolio.tasky.usecases

import javax.inject.Inject

class PasswordPatternValidation @Inject constructor(){
    fun isPasswordPatternValid(password: CharSequence): Boolean {
        return Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{9,}\$").matches(password)
    }
}