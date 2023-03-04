package com.portfolio.tasky.di.modules

import com.portfolio.tasky.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ValidatorsModule {
        @Provides
        @Singleton
        fun provideEmailPatternValidationImpl(): EmailPatternValidator {
                return EmailPatternValidatorImpl()
        }

        @Provides
        @Singleton
        fun providePasswordPatternValidationImpl(): PasswordPatternValidator {
                return PasswordPatternValidatorImpl()
        }
}