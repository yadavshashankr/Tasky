package com.portfolio.tasky.networking.usecases.domain

interface TaskyCallStatus {
    suspend fun onRequestCallStarted()

    suspend fun onResponse(responseCode : Int, responseMessage : String)

    suspend fun onFailure(responseCode: Int, errorMessage : String)
}