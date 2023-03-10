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
        mutableFieldsValid.value =  mutableEmail.value == true && mutablePassword.value == true && mutableName.value == true && networkObserver.value == NetworkStatus.Available
    }
    fun makeRegistrationCall(registerModel: RegisterRequest) : LiveData<Boolean>{
        return entryRepository.doRegistration(registerModel)
    }

}