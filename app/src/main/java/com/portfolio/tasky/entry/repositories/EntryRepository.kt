package com.portfolio.tasky.entry.repositories

import com.portfolio.tasky.entry.models.AuthenticationRequest
import com.portfolio.tasky.entry.models.AuthenticationResponse
import com.portfolio.tasky.entry.models.RegisterRequest

interface EntryRepository {
    suspend fun doLogin(authenticationRequest: AuthenticationRequest) : AuthenticationResponse?
    suspend fun doRegistration(registerRequest: RegisterRequest) : Boolean
}