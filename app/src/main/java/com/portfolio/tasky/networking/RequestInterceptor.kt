package com.portfolio.tasky.networking

import com.portfolio.tasky.entry.usecases.domain.UserPreferences
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class RequestInterceptor @Inject constructor(private val userPreferences: UserPreferences) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", userPreferences.getAuthenticatedUser()?.token as String)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}