package com.portfolio.tasky.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.tasky.usecases.EmailPatternValidator
import com.portfolio.tasky.usecases.PasswordPatternValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel  @Inject constructor(
    private val emailValidator: EmailPatternValidator,
    private val passwordPatternValidator: PasswordPatternValidator,
) : ViewModel() {

    private val mutableEmailChange = MutableLiveData(false)
    val emailChange : LiveData<Boolean?> = mutableEmailChange

    private val mutablePasswordChange = MutableLiveData(false)
    val passwordChange : LiveData<Boolean?> = mutablePasswordChange

    private val mutableNameChange = MutableLiveData(false)
    val nameChange : LiveData<Boolean?> = mutableNameChange

    private val mutableFieldsValid = MutableLiveData(false)
    val areFieldsValid : LiveData<Boolean?> = mutableFieldsValid



    fun emailChange(email : CharSequence){
        mutableEmailChange.value = emailValidator.isValidEmailPattern(email)
    }
    fun passwordChange(password : CharSequence){
        mutablePasswordChange.value = passwordPatternValidator.isPasswordPatternValid(password)
    }
    fun nameChange(name : CharSequence){
        mutableNameChange.value = name.isNotEmpty() && name.length > 3
    }
    fun areFieldsValid(){
        mutableFieldsValid.value =  mutablePasswordChange.value == true && mutablePasswordChange.value == true && mutableNameChange.value == true
    }
}