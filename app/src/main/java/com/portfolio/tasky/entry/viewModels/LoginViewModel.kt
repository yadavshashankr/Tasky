package com.portfolio.tasky.entry.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portfolio.tasky.entry.domain.EmailPatternValidator
import com.portfolio.tasky.entry.domain.usecases.PasswordPatternValidation
import com.portfolio.tasky.entry.models.AuthenticationRequest
import com.portfolio.tasky.entry.repositories.EntryRepository
import com.portfolio.tasky.entry.data.UserPreferences
import com.portfolio.tasky.entry.utils.SecuredPreference
import com.portfolio.tasky.usecases.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailValidator: EmailPatternValidator,
    private val passwordPatternValidation: PasswordPatternValidation,
    private val entryRepository: EntryRepository,
    networkStatus: LiveData<NetworkStatus>,
    private val userPreferences: UserPreferences,
    private val securedPreference: SecuredPreference
): ViewModel() {

    private val mutableEmail = MutableLiveData(false)
    val email : LiveData<Boolean> = mutableEmail

    private val mutablePassword = MutableLiveData(false)
    val password : LiveData<Boolean> = mutablePassword

    private val mutableFieldsValid = MutableLiveData(false)
    val validateFields : LiveData<Boolean> = mutableFieldsValid

    val networkObserver : LiveData<NetworkStatus> = networkStatus

    private val mutableLogin = MutableLiveData<Boolean?>()
    val loginObserver : LiveData<Boolean?> = mutableLogin

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }


    fun onEmailChange(email : String) {
        mutableEmail.value = emailValidator.isValidEmailPattern(email)
    }
    fun onPasswordChange(password : String) {
        mutablePassword.value = passwordPatternValidation.isPasswordPatternValid(password)
    }
    fun areFieldsValid() {
        val isNetworkAvailable = networkObserver.value == NetworkStatus.Available
        val areFieldsValidated = mutableEmail.value == true && mutablePassword.value == true
        mutableFieldsValid.value = areFieldsValidated  && isNetworkAvailable
    }

    fun login(authenticationModel: AuthenticationRequest){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val authenticatedUser = entryRepository.doLogin(authenticationModel)
            if(authenticatedUser != null){
                userPreferences.saveAuthenticatedUser(authenticatedUser)
                securedPreference.setAccessToken(authenticatedUser.token)
                authenticatedUser.token = null
                mutableLogin.postValue(true)
            }
        }
    }
}