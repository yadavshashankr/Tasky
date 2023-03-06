package com.portfolio.tasky.networking

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
            .header("x-api-key", "Bearer fbb80b2c798f41a1a16811e489e2bed6")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}