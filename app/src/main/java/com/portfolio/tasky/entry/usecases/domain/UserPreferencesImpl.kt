package com.portfolio.tasky.entry.usecases.domain

import android.content.Context
import com.google.gson.Gson
import com.portfolio.tasky.R
import com.portfolio.tasky.entry.models.AuthenticationResponse
import javax.inject.Inject

class UserPreferencesImpl @Inject constructor(val context: Context) : UserPreferences {

    private val sharedPreference by lazy { context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)}

    override fun saveAuthenticatedUser(authenticatedUser: AuthenticationResponse) {
        val jsonAuthenticatedUser = Gson().toJson(authenticatedUser)
        sharedPreference.edit().putString("authUser", jsonAuthenticatedUser).apply()
    }

    override fun getAuthenticatedUser(): AuthenticationResponse? {
        val authenticatedUserJson = sharedPreference.getString("authUser", "")
        return if(!authenticatedUserJson.equals("")){
            Gson().fromJson(authenticatedUserJson, AuthenticationResponse::class.java)
        }else{
            AuthenticationResponse("","","")
        }
    }

    override fun clearPreferences() {
        sharedPreference.edit().clear().apply()
    }
}