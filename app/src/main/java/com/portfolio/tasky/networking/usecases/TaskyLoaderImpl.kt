package com.portfolio.tasky.networking.usecases

import android.content.Context
import android.widget.Toast
import com.portfolio.tasky.networking.usecases.domain.TaskyLoader
/**
 * TaskyLoader will harbour the code for loading dialogs and would start or dismiss based on the value isLoading received in
 */
class TaskyLoaderImpl constructor(val context: Context) : TaskyLoader {

    override fun setLoading(isLoading: Boolean) {
        if (isLoading){
            Toast.makeText(context, "Loading Started", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Loading Completed", Toast.LENGTH_SHORT).show()
        }
    }

}