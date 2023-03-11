package com.portfolio.tasky.entry.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.tasky.entry.usecases.EmailPatternValidator
import com.portfolio.tasky.entry.usecases.NameValidation
import com.portfolio.tasky.entry.usecases.PasswordPatternValidation
import com.portfolio.tasky.entry.models.RegisterRequest
import com.portfolio.tasky.entry.repositories.EntryRepository
import com.portfolio.tasky.usecases.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel  @Inject constructor(
    private val emailValidator: EmailPatternValidator,
    private val passwordPatternValidation: PasswordPatternValidation,
    private val nameValidation: NameValidation,
    private val entryRepository: EntryRepository,
    networkStatus: LiveData<NetworkStatus>
) : ViewModel() {

    private val mutableEmail = MutableLiveData(false)
    val email : LiveData<Boolean> = mutableEmail

    private val mutablePassword = MutableLiveData(false)
    val password : LiveData<Boolean> = mutablePassword

    private val mutableName = MutableLiveData(false)
    val name : LiveData<Boolean> = mutableName

    private val mutableFieldsValid = MutableLiveData(false)
    val validateFields : LiveData<Boolean> = mutableFieldsValid


    val networkObserver : LiveData<NetworkStatus> = networkStatus

    private val mutableRegistration = MutableLiveData(false)
    val registrationObserver : LiveData<Boolean> = mutableRegistration



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
        val isNetworkAvailable = networkObserver.value == NetworkStatus.Available
        val areFieldsValidated = mutableEmail.value == true && mutablePassword.value == true && mutableName.value == true
        mutableFieldsValid.value = areFieldsValidated  && isNetworkAvailable
    }
    suspend fun registration(registerModel: RegisterRequest){
        mutableRegistration.postValue(entryRepository.doRegistration(registerModel))
    }
}