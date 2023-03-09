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

        when (responseCode){
            in 200..299 -> Toast.makeText(context, "Success\n$responseMessage", Toast.LENGTH_SHORT).show()
            in 400..599 -> Toast.makeText(context, responseMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onFailure(responseCode: Int, errorMessage: String) {
        taskyLoader.setLoading(false)
        Toast.makeText(context, "App Call Error", Toast.LENGTH_SHORT).show()
    }
}