package com.portfolio.tasky.entry.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.portfolio.tasky.entry.network.EntryApiCall
import com.portfolio.tasky.entry.models.AuthenticationRequest
import com.portfolio.tasky.entry.models.AuthenticationResponse
import com.portfolio.tasky.entry.models.RegisterRequest
import com.portfolio.tasky.networking.usecases.domain.TaskyCallStatus
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class EntryRepository @Inject constructor(private val entryApiCall: EntryApiCall)  {
    private val registrationLiveData = MutableLiveData<Boolean?>()
    private val loginLiveData = MutableLiveData<AuthenticationResponse?>()

    fun doRegistration(taskyCallStatusCallback: TaskyCallStatus, registerModel: RegisterRequest) : LiveData<Boolean?>{

        taskyCallStatusCallback.onRequestCallStarted()

        entryApiCall.registration(registerModel).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.code() == 200){
                    taskyCallStatusCallback.onResponse(response.code(), response.body().toString())
                    registrationLiveData.postValue(true)
                }else{
                    val jObjError = response.errorBody()?.string()?.let { JSONObject(it) }
                    taskyCallStatusCallback.onResponse(response.code(), jObjError?.getString("message") as String)
                    registrationLiveData.postValue(false)
                }

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                taskyCallStatusCallback.onFailure(0, t.message.toString())
                registrationLiveData.postValue(false)
            }

        })

        return registrationLiveData
    }

    fun doLogin(taskyCallStatusCallback: TaskyCallStatus, authenticationModel: AuthenticationRequest) : LiveData<AuthenticationResponse?>{

        taskyCallStatusCallback.onRequestCallStarted()

        entryApiCall.login(authenticationModel).enqueue(object : Callback<AuthenticationResponse>{
            override fun onResponse(call: Call<AuthenticationResponse>, response: Response<AuthenticationResponse>) {
                if(response.code() == 200){
                    taskyCallStatusCallback.onResponse(response.code(), response.body().toString())
                    loginLiveData.postValue(response.body())
                }else{
                    val jObjError = response.errorBody()?.string()?.let { JSONObject(it) }
                    taskyCallStatusCallback.onResponse(response.code(), jObjError?.getString("message") as String)
                    loginLiveData.postValue(null)
                }
            }
            override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                taskyCallStatusCallback.onFailure(0, t.message.toString())
                loginLiveData.postValue(null)
            }
        })

        return loginLiveData
    }
}