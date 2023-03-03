package com.portfolio.tasky

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import com.portfolio.tasky.globals.Constants.Companion.Entry.LOGIN
import com.portfolio.tasky.globals.Constants.Companion.Entry.REGISTRATION
import com.portfolio.tasky.globals.DataUtils.Companion.Validators.isEmailValid
import com.portfolio.tasky.globals.DataUtils.Companion.Validators.isNameValid
import com.portfolio.tasky.globals.DataUtils.Companion.Validators.isPasswordValid
import com.portfolio.tasky.views.TaskyAppCompatEditText

class TaskyValidationWatcherImpl(
    taskyAppCompatEditText: TaskyAppCompatEditText,
    entry : String,
    fieldsValidator : FieldsValidator
) : TextWatcher {

    private var appCompatEditText: AppCompatEditText
    var taskAppCompatEditText: TaskyAppCompatEditText = taskyAppCompatEditText
    private var isEntryValid : String = ""
    var fieldsValidator : FieldsValidator? = null

    init {
        this.appCompatEditText = taskyAppCompatEditText.subLayout.etInput
        this.isEntryValid = entry
        this.fieldsValidator = fieldsValidator
    }

    override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {/*TODO("Not yet implemented")*/}

    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
        validateText(p0, taskAppCompatEditText.id, taskAppCompatEditText)
    }

    override fun afterTextChanged(p0: Editable?) {/*TODO("Not yet implemented")*/}

    private fun validateText(var1: CharSequence, viewID: Int, taskAppCompatEditText: TaskyAppCompatEditText) {
        if(viewID == R.id.et_email){
            isEmailValid = isEmailValid(var1)
            taskAppCompatEditText.setError(var1.isNotEmpty() && !isEmailValid)
            taskAppCompatEditText.setValid(isEmailValid)
        }
        if(viewID == R.id.et_password){
            isPasswordValid = isPasswordValid(var1)
            taskAppCompatEditText.setError(var1.isNotEmpty() && !isPasswordValid)
            taskAppCompatEditText.setValid(isPasswordValid)
        }
        if(viewID == R.id.et_name){
            isNameValid = isNameValid(var1)
            taskAppCompatEditText.setError(var1.isNotEmpty() && !isNameValid)
            taskAppCompatEditText.setValid(isNameValid)
        }
        buttonActivator()
    }

    private fun buttonActivator() {
        val isValid =
       /*Login*/      isEntryValid == LOGIN && isEmailValid && isPasswordValid
         /*OR*/          ||
    /*Registration*/  isEntryValid == REGISTRATION && isEmailValid && isPasswordValid && isNameValid
        fieldsValidator?.fieldsValidated(isValid)
    }
}