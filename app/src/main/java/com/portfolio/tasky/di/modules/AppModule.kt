package com.portfolio.tasky.di.modules

import android.content.Context
import androidx.lifecycle.LiveData
import com.portfolio.tasky.data.NetworkChangeReceiver
import com.portfolio.tasky.usecases.NetworkStatus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideNetworkChangeReceiver(context: Context): LiveData<NetworkStatus> {
        return NetworkChangeReceiver(context)
    }
}