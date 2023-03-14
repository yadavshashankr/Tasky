package com.portfolio.tasky.entry.data

import okhttp3.Interceptor
import okhttp3.Response

interface RequestInterceptor : Interceptor {

    fun interceptRequest(chain : Interceptor.Chain) : Response
}