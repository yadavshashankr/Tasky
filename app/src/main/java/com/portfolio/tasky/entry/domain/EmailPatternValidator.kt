package com.portfolio.tasky.entry.domain

interface EmailPatternValidator {
    fun isValidEmailPattern(email : CharSequence) : Boolean
}