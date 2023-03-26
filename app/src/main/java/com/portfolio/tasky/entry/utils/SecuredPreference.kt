package com.portfolio.tasky.entry.utils

interface SecuredPreference {

    suspend fun generateSecuredPreference()

    suspend fun setAccessToken(accessToken: String?)

    suspend fun getAccessToken() : String

}