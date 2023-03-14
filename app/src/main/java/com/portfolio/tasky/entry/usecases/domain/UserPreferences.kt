package com.portfolio.tasky.entry.usecases.domain

import com.portfolio.tasky.entry.models.AuthenticationResponse

interface UserPreferences {

    fun saveAuthenticatedUser(authenticatedUser: AuthenticationResponse)

    fun getAuthenticatedUser() : AuthenticationResponse?

    fun clearPreferences()
}