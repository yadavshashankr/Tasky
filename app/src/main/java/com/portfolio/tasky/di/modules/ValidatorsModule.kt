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
        fun provideNameValidation(): NameValidation{
                return NameValidation()
        }
}