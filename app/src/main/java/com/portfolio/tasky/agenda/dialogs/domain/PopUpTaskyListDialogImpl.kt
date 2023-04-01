package com.portfolio.tasky.agenda.dialogs.domain

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.PopupWindow
import androidx.lifecycle.MutableLiveData
import com.portfolio.tasky.R
import com.portfolio.tasky.agenda.adapters.PopUpTaskyListAdapter
import com.portfolio.tasky.globals.Constants
import javax.inject.Inject

@SuppressLint("InflateParams")
class PopUpTaskyListDialogImpl @Inject constructor(private var applicationContext : Context) : PopUpTaskyListDialog {

    private lateinit var popUpDialogArrayList : ArrayList<String>
    private var selectedSubAgendaMutableLiveData = MutableLiveData<String>()

    private lateinit var rvList: ListView
    private lateinit var popupWindow: PopupWindow
    private var inflater = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var view: View = inflater.inflate(R.layout.base_list_pop_up_layout, null)

    override fun showAgendaDialog(agenda : Any) : PopupWindow{
        when(agenda){
            is Constants.Companion.AgendaType -> this.popUpDialogArrayList = getAgendaTypeList()
            is Constants.Companion.AgendaState -> this.popUpDialogArrayList = getAgendaStateList()
            is Constants.Companion.AgendaPreTimeParams -> this.popUpDialogArrayList = getAgendaPreTimesList()
        }
        return processPopUpWindow()
    }

    override fun dialogObserver() : MutableLiveData<String> { return selectedSubAgendaMutableLiveData }

    private fun processPopUpWindow() : PopupWindow {
        rvList = view.findViewById(R.id.rv_list)
        val popUpTaskyListAdapter = PopUpTaskyListAdapter(applicationContext, this.popUpDialogArrayList)

        rvList.adapter = popUpTaskyListAdapter
        popUpTaskyListAdapter.notifyDataSetChanged()

        rvList.setOnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
            popupWindow.dismiss()
            selectedSubAgendaMutableLiveData.postValue(this.popUpDialogArrayList[position])
        }

        rvList.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

        popupWindow = PopupWindow(view, rvList.measuredWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popupWindow.elevation = 10f
        popupWindow.isOutsideTouchable = true
        popupWindow.contentView = view
        return popupWindow
    }

    private fun getAgendaTypeList() : ArrayList<String> {
        val agendaArrayList : ArrayList<String> = ArrayList()
        agendaArrayList.add(applicationContext.getString(R.string.task))
        agendaArrayList.add(applicationContext.getString(R.string.event))
        agendaArrayList.add(applicationContext.getString(R.string.reminder))
        return agendaArrayList
    }

    private fun getAgendaStateList() : ArrayList<String> {
        val agendaArrayList : ArrayList<String> = ArrayList()
        agendaArrayList.add(applicationContext.getString(R.string.open))
        agendaArrayList.add(applicationContext.getString(R.string.edit))
        agendaArrayList.add(applicationContext.getString(R.string.delete))
        return agendaArrayList
    }

    private fun getAgendaPreTimesList() : ArrayList<String> {
        val agendaArrayList : ArrayList<String> = ArrayList()
        agendaArrayList.add(applicationContext.getString(R.string.ten_mins_before))
        agendaArrayList.add(applicationContext.getString(R.string.thirty_mins_before))
        agendaArrayList.add(applicationContext.getString(R.string.one_hour_before))
        agendaArrayList.add(applicationContext.getString(R.string.six_hours_before))
        agendaArrayList.add(applicationContext.getString(R.string.one_day_before))
        return agendaArrayList
    }
}