package com.portfolio.tasky.networking

import com.portfolio.tasky.entry.usecases.domain.UserPreferences
import com.portfolio.tasky.networking.domain.RequestInterceptor
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class RequestInterceptorImpl @Inject constructor(private val userPreferences: UserPreferences) :
    RequestInterceptor {
    @Throws(IOException::class)
    override fun interceptRequest(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", userPreferences.getAuthenticatedUser()?.token as String)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return interceptRequest(chain)
    }
}