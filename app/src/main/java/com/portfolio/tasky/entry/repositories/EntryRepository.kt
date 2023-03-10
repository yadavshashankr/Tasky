package com.portfolio.tasky.entry.repositories

import androidx.lifecycle.LiveData
import com.portfolio.tasky.entry.models.AuthenticationRequest
import com.portfolio.tasky.entry.models.AuthenticationResponse
import com.portfolio.tasky.entry.models.RegisterRequest

interface EntryRepository {
    fun doLogin(authenticationRequest: AuthenticationRequest) : LiveData<AuthenticationResponse?>
    fun doRegistration(registerRequest: RegisterRequest) : LiveData<Boolean>
}