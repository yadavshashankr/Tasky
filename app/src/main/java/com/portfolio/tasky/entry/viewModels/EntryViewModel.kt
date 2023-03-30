package com.portfolio.tasky.entry.viewModels

import android.widget.PopupWindow
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.portfolio.tasky.agenda.dialogs.domain.PopUpTaskyListDialog
import com.portfolio.tasky.globals.Constants
import com.portfolio.tasky.usecases.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(networkStatus: LiveData<NetworkStatus>, private val popUpTaskyListDialog: PopUpTaskyListDialog) : ViewModel() {
    val networkStatusObserver : LiveData<NetworkStatus> = networkStatus

    fun showAgendaDialog(agenda: Constants.Companion.Agenda) : PopupWindow {
        return popUpTaskyListDialog.showAgendaDialog(agenda)
    }

    fun agendaDialogObserver() : LiveData<String>{
        return popUpTaskyListDialog.dialogObserver()
    }
}