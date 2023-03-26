package com.portfolio.tasky.entry.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.portfolio.tasky.usecases.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(networkStatus: LiveData<NetworkStatus>) : ViewModel() {
    val networkStatusObserver : LiveData<NetworkStatus> = networkStatus
}