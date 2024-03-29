package com.portfolio.tasky.data

import com.portfolio.tasky.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class RequestInterceptorApiKey @Inject constructor() : Interceptor{

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("x-api-key", BuildConfig.api_key)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}