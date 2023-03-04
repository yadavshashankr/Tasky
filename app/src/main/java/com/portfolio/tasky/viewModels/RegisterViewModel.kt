package com.portfolio.tasky.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.tasky.usecases.EmailPatternValidatorImpl
import com.portfolio.tasky.usecases.NamePatternValidator
import com.portfolio.tasky.usecases.PasswordPatternValidatorImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel  @Inject constructor(
    private val validatorImpl: EmailPatternValidatorImpl,
    private val passwordPatternValidatorImpl: PasswordPatternValidatorImpl,
    private val namePatternValidator: NamePatternValidator
) : ViewModel() {

    private val mutableEmailChange = MutableLiveData<Boolean?>()
    val emailChange : LiveData<Boolean?> = mutableEmailChange

    private val mutablePasswordChange = MutableLiveData<Boolean?>()
    val passwordChange : LiveData<Boolean?> = mutablePasswordChange

    private val mutableNameChange = MutableLiveData<Boolean?>()
    val nameChange : LiveData<Boolean?> = mutableNameChange

    private val mutableFieldsValid = MutableLiveData<Boolean?>()
    val areFieldsValid : LiveData<Boolean?> = mutableFieldsValid

    fun emailChange(email : CharSequence?){
        mutableEmailChange.value = validatorImpl.isValidEmailPattern(email)
    }
    fun passwordChange(password : CharSequence?){
        mutablePasswordChange.value = passwordPatternValidatorImpl.isPasswordPatternValid(password)
    }
    fun nameChange(name : CharSequence?){
        mutableNameChange.value = namePatternValidator.isValidName(name)
    }
    fun areFieldsValid(){
        mutableFieldsValid.value =  (mutableEmailChange.value != null && mutablePasswordChange.value == true) && (mutablePasswordChange.value != null && mutablePasswordChange.value == true) && (mutableNameChange.value != null && mutableNameChange.value == true)
    }
}