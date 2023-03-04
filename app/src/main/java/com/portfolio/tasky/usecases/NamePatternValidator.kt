package com.portfolio.tasky.usecases

interface NamePatternValidator {
    fun isValidName(name: CharSequence?) : Boolean?
}