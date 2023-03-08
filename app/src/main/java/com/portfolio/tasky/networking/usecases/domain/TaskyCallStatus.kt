package com.portfolio.tasky.networking.usecases.domain

interface TaskyCallStatus {
    fun onRequestCallStarted()

    fun onResponse(responseCode : Int, responseMessage : String)

    fun onFailure(responseCode: Int, errorMessage : String)
}