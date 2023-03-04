package com.portfolio.tasky.usecases

interface PasswordPatternValidator {
    fun isPasswordPatternValid(password : CharSequence) : Boolean
}