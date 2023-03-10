package com.portfolio.tasky.entry.di.modules

import com.portfolio.tasky.entry.network.EntryApiCall
import com.portfolio.tasky.entry.repositories.EntryRepository
import com.portfolio.tasky.entry.repositories.EntryRepositoryImpl
import com.portfolio.tasky.entry.usecases.EmailPatternValidator
import com.portfolio.tasky.entry.usecases.EmailPatternValidatorImpl
import com.portfolio.tasky.entry.usecases.NameValidation
import com.portfolio.tasky.entry.usecases.PasswordPatternValidation
import com.portfolio.tasky.networking.usecases.domain.TaskyCallStatus
import com.portfolio.tasky.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EntryModules {
        @Provides
        @Singleton
        fun provideEmailPatternValidation(): EmailPatternValidator {
                return EmailPatternValidatorImpl()
        }

        @Provides
        @Singleton
        fun providePasswordPatternValidation(): PasswordPatternValidation {
                return PasswordPatternValidation()
        }

        @Provides
        @Singleton
        fun provideNameValidation(): NameValidation {
                return NameValidation()
        }

        @Provides
        @Singleton
        fun providesEntryApiCall(retrofit: Retrofit): EntryApiCall {
                return retrofit.create(EntryApiCall::class.java)
        }

        @Provides
        @Singleton
        fun providesEntryRepository(entryApiCall: EntryApiCall, taskyCallStatus: TaskyCallStatus): EntryRepository{
                return EntryRepositoryImpl(entryApiCall, taskyCallStatus)
        }
}