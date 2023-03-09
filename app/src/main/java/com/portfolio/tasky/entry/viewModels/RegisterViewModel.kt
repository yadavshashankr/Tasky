package com.portfolio.tasky.entry.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portfolio.tasky.entry.usecases.EmailPatternValidator
import com.portfolio.tasky.entry.usecases.NameValidation
import com.portfolio.tasky.entry.usecases.PasswordPatternValidation
import com.portfolio.tasky.entry.models.RegisterRequest
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
class RegisterViewModel  @Inject constructor(
    private val emailValidator: EmailPatternValidator,
    private val passwordPatternValidation: PasswordPatternValidation,
    private val nameValidation: NameValidation,
    private val taskyCallStatus: TaskyCallStatus,
    private val entryApiCall: EntryApiCall
) : ViewModel() {

    private val mutableEmail = MutableLiveData(false)
    val email : LiveData<Boolean> = mutableEmail

    private val mutablePassword = MutableLiveData(false)
    val password : LiveData<Boolean> = mutablePassword

    private val mutableName = MutableLiveData(false)
    val name : LiveData<Boolean> = mutableName

    private val mutableFieldsValid = MutableLiveData(false)
    val validateFields : LiveData<Boolean> = mutableFieldsValid

    private val mutableRegistration = MutableLiveData(false)
    private val registrationObserver : LiveData<Boolean> = mutableRegistration



    fun onEmailChange(email : String) {
        mutableEmail.value = emailValidator.isValidEmailPattern(email)
    }
    fun onPasswordChange(password : String) {
        mutablePassword.value = passwordPatternValidation.isPasswordPatternValid(password)
    }
    fun onNameChange(name : String) {
        mutableName.value = nameValidation.isValidName(name)
    }
    fun areFieldsValid() {
        mutableFieldsValid.value =  mutableEmail.value == true && mutablePassword.value == true && mutableName.value == true
    }
    fun makeRegistrationCall(registerModel: RegisterRequest) : LiveData<Boolean>{
        taskyCallStatus.onRequestCallStarted()
        viewModelScope.launch(Dispatchers.IO){

            var response : Response<Void>

            withContext(Dispatchers.IO) {
                response = entryApiCall.registration(registerModel)
            }

            withContext(Dispatchers.Main) {
                if(!response.isSuccessful){
                    val jObjError = response.errorBody()?.string()?.let { JSONObject(it) }
                    taskyCallStatus.onFailure(response.code(), jObjError?.getString("message") as String)
                }else{
                    taskyCallStatus.onResponse(response.code(), response.message())
                    mutableRegistration.value = true
                }
            }
        }
        return registrationObserver
    }
}