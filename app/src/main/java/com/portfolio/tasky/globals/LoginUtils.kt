package com.portfolio.tasky.globals

class LoginUtils {

    companion object{
        var token : String = ""
        fun setAccessToken(token : String){
            this.token = token
        }

        fun getAccessToken() : String{
            return "Bearer $token"
        }
    }
}