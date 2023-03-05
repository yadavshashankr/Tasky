package com.portfolio.tasky.entry.usecases

interface EmailPatternValidator {
    fun isValidEmailPattern(email : CharSequence) : Boolean
}