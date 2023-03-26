package com.portfolio.tasky.networking.usecases.domain

interface TaskyLoader {
    suspend fun setLoading(isLoading : Boolean)
}