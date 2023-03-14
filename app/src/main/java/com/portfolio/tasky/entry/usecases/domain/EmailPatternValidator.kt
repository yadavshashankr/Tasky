package com.portfolio.tasky.entry.usecases.domain

interface EmailPatternValidator {
    fun isValidEmailPattern(email : CharSequence) : Boolean
}