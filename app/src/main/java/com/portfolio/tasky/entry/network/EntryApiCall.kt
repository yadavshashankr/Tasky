package com.portfolio.tasky.entry.network

import com.portfolio.tasky.entry.models.AuthenticationRequest
import com.portfolio.tasky.entry.models.AuthenticationResponse
import com.portfolio.tasky.entry.models.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EntryApiCall {

    @POST("/register")
    suspend fun registration(@Body registerModel: RegisterRequest) : Response<Void>

    @POST("/login")
    suspend fun login(@Body authenticationModel: AuthenticationRequest) : Response<AuthenticationResponse>
}