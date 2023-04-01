package com.portfolio.tasky.agenda.dialogs.domain

import android.widget.PopupWindow
import androidx.lifecycle.MutableLiveData

interface PopUpTaskyListDialog {

    fun showAgendaDialog(agenda: Any) : PopupWindow

    fun dialogObserver() : MutableLiveData<String>

}