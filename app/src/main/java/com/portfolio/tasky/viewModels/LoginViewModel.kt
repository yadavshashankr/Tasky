package com.portfolio.tasky.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.tasky.usecases.EmailPatternValidatorImpl
import com.portfolio.tasky.usecases.PasswordPatternValidatorImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validatorImpl: EmailPatternValidatorImpl,
    private val passwordPatternValidatorImpl: PasswordPatternValidatorImpl
): ViewModel() {
    private val mutableEmailChange = MutableLiveData<Boolean?>()
    val emailChange : LiveData<Boolean?> = mutableEmailChange

    private val mutablePasswordChange = MutableLiveData<Boolean?>()
    val passwordChange : LiveData<Boolean?> = mutablePasswordChange

    private val mutableFieldsValid = MutableLiveData<Boolean?>()
    val areFieldsValid : LiveData<Boolean?> = mutableFieldsValid

    fun emailChange(email : CharSequence?){
        mutableEmailChange.value = validatorImpl.isValidEmailPattern(email)
    }
    fun passwordChange(password : CharSequence?){
        mutablePasswordChange.value = passwordPatternValidatorImpl.isPasswordPatternValid(password)
    }
    fun areFieldsValid(){
        mutableFieldsValid.value =  (mutableEmailChange.value != null && mutablePasswordChange.value == true) && (mutablePasswordChange.value != null && mutablePasswordChange.value == true)
    }

}