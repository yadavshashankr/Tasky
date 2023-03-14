package com.portfolio.tasky.networking.domain

import okhttp3.Interceptor
import okhttp3.Response

interface RequestInterceptor : Interceptor {

    fun interceptRequest(chain : Interceptor.Chain) : Response
}