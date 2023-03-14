package com.portfolio.tasky.app.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.portfolio.tasky.data.NetworkChangeReceiver
import com.portfolio.tasky.entry.usecases.domain.UserPreferences
import com.portfolio.tasky.globals.Constants
import com.portfolio.tasky.entry.data.RequestInterceptorImpl
import com.portfolio.tasky.entry.data.RequestInterceptor
import com.portfolio.tasky.networking.usecases.domain.TaskyLoader
import com.portfolio.tasky.networking.usecases.TaskyLoaderImpl
import com.portfolio.tasky.networking.usecases.domain.TaskyCallStatus
import com.portfolio.tasky.networking.usecases.TaskyCallStatusImpl
import com.portfolio.tasky.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    fun provideNetworkChangeReceiver(context: Context): LiveData<NetworkStatus> {
        return NetworkChangeReceiver(context)
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
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideRequestInterceptor(userPreferences: UserPreferences): RequestInterceptor {
        return RequestInterceptorImpl(userPreferences)
    }

    @Singleton
    @Provides
    fun providesTaskyLoaderImpl(context: Context): TaskyLoader {
        return TaskyLoaderImpl(context)
    }

    @Singleton
    @Provides
    fun providesTaskyCallStatusImpl(taskyLoader: TaskyLoader, context: Context): TaskyCallStatus {
        return TaskyCallStatusImpl(taskyLoader, context)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(requestInterceptor: RequestInterceptor, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(Constants.Companion.ApiProperties.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.Companion.ApiProperties.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.Companion.ApiProperties.DEFAULT_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(requestInterceptor)
            .addInterceptor(httpLoggingInterceptor)
        return httpClient.build()
    }
}