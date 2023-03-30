package com.portfolio.tasky.agenda.di

import android.content.Context
import com.portfolio.tasky.agenda.dialogs.domain.PopUpTaskyListDialog
import com.portfolio.tasky.agenda.dialogs.domain.PopUpTaskyListDialogImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AgendaModules {

    @Provides
    fun providesPopUpTaskyDialog(@ApplicationContext applicationContext: Context): PopUpTaskyListDialog {
        return PopUpTaskyListDialogImpl(applicationContext)
    }
}