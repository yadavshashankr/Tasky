package com.portfolio.tasky.networking

import com.portfolio.tasky.globals.LoginUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class RequestInterceptor @Inject constructor() :
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", LoginUtils.getAccessToken())
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}