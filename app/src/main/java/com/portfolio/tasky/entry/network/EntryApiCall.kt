package com.portfolio.tasky.entry.network

import com.portfolio.tasky.entry.models.AuthenticationRequest
import com.portfolio.tasky.entry.models.AuthenticationResponse
import com.portfolio.tasky.entry.models.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EntryApiCall {

    @POST("/register")
    fun registration(@Body registerModel: RegisterRequest) : Call<Void>

    @POST("/login")
    fun login(@Body authenticationModel: AuthenticationRequest) : Call<AuthenticationResponse>
}