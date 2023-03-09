package com.portfolio.tasky.entry.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portfolio.tasky.entry.usecases.EmailPatternValidator
import com.portfolio.tasky.entry.usecases.PasswordPatternValidation
import com.portfolio.tasky.entry.models.AuthenticationRequest
import com.portfolio.tasky.entry.models.AuthenticationResponse
import com.portfolio.tasky.entry.network.EntryApiCall
import com.portfolio.tasky.networking.usecases.domain.TaskyCallStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailValidator: EmailPatternValidator,
    private val passwordPatternValidation: PasswordPatternValidation,
    private val taskyCallStatus: TaskyCallStatus,
    private val entryApiCall: EntryApiCall
): ViewModel() {

    private val mutableEmail = MutableLiveData(false)
    val email : LiveData<Boolean> = mutableEmail

    private val mutablePassword = MutableLiveData(false)
    val password : LiveData<Boolean> = mutablePassword

    private val mutableFieldsValid = MutableLiveData(false)
    val validateFields : LiveData<Boolean> = mutableFieldsValid

    private val mutableLoginObserver = MutableLiveData<AuthenticationResponse>()
    private val loginObserver : LiveData<AuthenticationResponse> =mutableLoginObserver



    fun onEmailChange(email : String) {
        mutableEmail.value = emailValidator.isValidEmailPattern(email)
    }
    fun onPasswordChange(password : String) {
        mutablePassword.value = passwordPatternValidation.isPasswordPatternValid(password)
    }
    fun areFieldsValid() {
        mutableFieldsValid.value =  mutableEmail.value == true && mutablePassword.value == true
    }
    fun makeLoginCall(authenticationModel: AuthenticationRequest) : LiveData<AuthenticationResponse>{
        taskyCallStatus.onRequestCallStarted()

         viewModelScope.launch(Dispatchers.IO){

             var response : Response<AuthenticationResponse>

             withContext(Dispatchers.IO) {
                 response = entryApiCall.login(authenticationModel)
             }

             withContext(Dispatchers.Main) {
                 if(!response.isSuccessful){
                     val jObjError = response.errorBody()?.string()?.let { JSONObject(it) }
                     taskyCallStatus.onFailure(response.code(), jObjError?.getString("message") as String)
                 }else{
                     taskyCallStatus.onResponse(response.code(), response.message())
                     mutableLoginObserver.value = response.body()
                 }
             }
         }
         return loginObserver
    }
}