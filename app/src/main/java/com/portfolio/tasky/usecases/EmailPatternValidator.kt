package com.portfolio.tasky.usecases

interface EmailPatternValidator {
    fun isValidEmailPattern(email : CharSequence) : Boolean
}