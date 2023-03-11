package com.portfolio.tasky.entry.repositories

import androidx.lifecycle.MutableLiveData
import com.portfolio.tasky.entry.network.EntryApiCall
import com.portfolio.tasky.entry.models.AuthenticationRequest
import com.portfolio.tasky.entry.models.AuthenticationResponse
import com.portfolio.tasky.entry.models.RegisterRequest
import com.portfolio.tasky.networking.usecases.domain.TaskyCallStatus
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


class EntryRepositoryImpl@Inject constructor(private val entryApiCall: EntryApiCall, private val taskyCallStatus: TaskyCallStatus) : EntryRepository  {
    private val registrationLiveData = MutableLiveData<Boolean>()
    private val loginLiveData = MutableLiveData<AuthenticationResponse?>()

    override suspend fun doRegistration(registerRequest: RegisterRequest): Boolean {

        taskyCallStatus.onRequestCallStarted()

        val response : Response<Void> = entryApiCall.registration(registerRequest)

        return if(response.isSuccessful){
            taskyCallStatus.onResponse(response.code(), response.message())
            registrationLiveData.postValue(true)
            true
        }else{
            val jObjError = response.errorBody()?.string()?.let { JSONObject(it) }
            taskyCallStatus.onFailure(response.code(), jObjError?.getString("message") as String)
            registrationLiveData.postValue(false)
            false
        }
    }

    override suspend fun doLogin(authenticationRequest: AuthenticationRequest) : AuthenticationResponse? {

        taskyCallStatus.onRequestCallStarted()

        val response : Response<AuthenticationResponse> = entryApiCall.login(authenticationRequest)

        return if(response.isSuccessful){
            taskyCallStatus.onResponse(response.code(), response.message())
            loginLiveData.postValue(response.body())
            response.body()
        }else{
            val jObjError = response.errorBody()?.string()?.let { JSONObject(it) }
            taskyCallStatus.onFailure(response.code(), jObjError?.getString("message") as String)
            null
        }
    }
}