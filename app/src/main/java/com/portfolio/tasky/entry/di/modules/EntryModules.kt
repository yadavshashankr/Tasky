package com.portfolio.tasky.entry.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.portfolio.tasky.R
import com.portfolio.tasky.entry.network.EntryApiCall
import com.portfolio.tasky.entry.repositories.EntryRepository
import com.portfolio.tasky.entry.repositories.EntryRepositoryImpl
import com.portfolio.tasky.entry.domain.EmailPatternValidator
import com.portfolio.tasky.entry.domain.usecases.EmailPatternValidatorImpl
import com.portfolio.tasky.entry.domain.usecases.NameValidation
import com.portfolio.tasky.entry.domain.usecases.PasswordPatternValidation
import com.portfolio.tasky.entry.data.UserPreferences
import com.portfolio.tasky.entry.data.UserPreferencesImpl
import com.portfolio.tasky.entry.utils.SecuredPreference
import com.portfolio.tasky.entry.utils.SecuredPreferenceImpl
import com.portfolio.tasky.networking.usecases.domain.TaskyCallStatus
import com.portfolio.tasky.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

        @Provides
        @Singleton
        fun providesUserPreference(@ApplicationContext context: Context): UserPreferences {
                return UserPreferencesImpl(context)
        }

        @Provides
        @Singleton
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
                return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        }

        @Provides
        @Singleton
        fun provideSecuredPreference(sharedPreferences: SharedPreferences,  context: Context): SecuredPreference {
                return SecuredPreferenceImpl(sharedPreferences, context)
        }
}