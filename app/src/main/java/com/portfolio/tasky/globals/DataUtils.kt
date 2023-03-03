package com.portfolio.tasky.globals

import android.util.Patterns

class DataUtils {

    companion object{
        object Validators{
            var isEmailValid : Boolean = false
            var isNameValid : Boolean = false
            var isPasswordValid : Boolean = false

            fun clearValidatorParams(){
                isEmailValid = false
                isNameValid = false
                isPasswordValid = false
            }

            fun isEmailValid(emailID : CharSequence?) : Boolean{
                return Patterns.EMAIL_ADDRESS.matcher(emailID as CharSequence).matches()
            }
            fun isPasswordValid(password : CharSequence) : Boolean{
                return Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{9,}\$").matches(password)
            }
            fun isNameValid(name : CharSequence) : Boolean{
                return Regex("^(?=.*[a-z]).{3,}\$").matches(name)
            }
        }
    }
}