package com.portfolio.tasky.app.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.portfolio.tasky.globals.Constants
import com.portfolio.tasky.networking.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().setLenient().excludeFieldsWithoutExposeAnnotation().create()
    }


    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.Companion.ApiProperties.URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(requestInterceptor: RequestInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(Constants.Companion.ApiProperties.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.Companion.ApiProperties.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.Companion.ApiProperties.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(requestInterceptor)
        return httpClient.build()
    }
}