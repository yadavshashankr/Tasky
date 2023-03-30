package com.portfolio.tasky.agenda.dialogs.domain

import android.widget.PopupWindow
import androidx.lifecycle.MutableLiveData
import com.portfolio.tasky.globals.Constants

interface PopUpTaskyListDialog {

    fun showAgendaDialog(agenda: Constants.Companion.Agenda) : PopupWindow

    fun dialogObserver() : MutableLiveData<String>

}