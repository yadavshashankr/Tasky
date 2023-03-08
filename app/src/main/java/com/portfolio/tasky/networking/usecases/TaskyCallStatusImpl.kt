package com.portfolio.tasky.networking.usecases

import android.content.Context
import android.widget.Toast
import com.portfolio.tasky.networking.usecases.domain.TaskyLoader
import com.portfolio.tasky.networking.usecases.domain.TaskyCallStatus
import javax.inject.Inject

/**
 * TaskStatusImpl will harbour the code for un-successful api calls and
 * would present errors, messages in dialogs
 * with respect to Response codes.
 */
class TaskyCallStatusImpl @Inject constructor(private val taskyLoader: TaskyLoader, private val context: Context) : TaskyCallStatus {

    override fun onRequestCallStarted() {
        taskyLoader.setLoading(true)
    }

    override fun onResponse(responseCode: Int, responseMessage: String) {
        taskyLoader.setLoading(false)
        Toast.makeText(context, "Status Completed", Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(responseCode: Int, errorMessage: String) {
        taskyLoader.setLoading(false)
        Toast.makeText(context, "Error Completed", Toast.LENGTH_SHORT).show()
    }
}