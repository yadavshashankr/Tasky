package com.portfolio.tasky.entry.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.tasky.entry.usecases.EmailPatternValidator
import com.portfolio.tasky.entry.usecases.PasswordPatternValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailValidator: EmailPatternValidator,
    private val passwordPatternValidation: PasswordPatternValidation
): ViewModel() {

    private val mutableEmailChange = MutableLiveData(false)
    val emailChange : LiveData<Boolean> = mutableEmailChange

    private val mutablePasswordChange = MutableLiveData(false)
    val passwordChange : LiveData<Boolean> = mutablePasswordChange

    private val mutableFieldsValid = MutableLiveData(false)
    val areFieldsValid : LiveData<Boolean> = mutableFieldsValid



    fun emailChange(email : String) {
        mutableEmailChange.value = emailValidator.isValidEmailPattern(email)
    }
    fun passwordChange(password : String) {
        mutablePasswordChange.value = passwordPatternValidation.isPasswordPatternValid(password)
    }
    fun areFieldsValid() {
        mutableFieldsValid.value =  mutablePasswordChange.value == true && mutablePasswordChange.value == true
    }

}