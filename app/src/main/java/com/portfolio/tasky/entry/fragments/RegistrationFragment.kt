package com.portfolio.tasky.entry.fragments

import android.os.Bundle
import android.text.Editable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.transition.Slide
import com.portfolio.tasky.*
import com.portfolio.tasky.databinding.LayoutRegistrationBinding
import com.portfolio.tasky.entry.EntryActivity
import com.portfolio.tasky.viewModels.RegisterViewModel
import com.portfolio.tasky.views.TaskyAppCompatEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment(), FragmentInflater by FragmentInflaterImpl(), FieldsValidator, TextChanged {
    private lateinit var viewBinding: LayoutRegistrationBinding

    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.layout_registration, container, false)
        viewBinding.lifecycleOwner = this
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        viewBinding.btnReg.setOnClickListener {
            startLoginFragment()
        }
    }

    private fun setObservers() {
        viewModel.emailChange.observe(viewLifecycleOwner){
            val emailField = viewBinding.etEmail
            it?.let { isValid -> emailField.setValid(isValid)
                emailField.setError(emailField.subLayout.etInput.text?.isNotEmpty() == true && !isValid)}
        }
        viewModel.passwordChange.observe(viewLifecycleOwner){
            val passwordField = viewBinding.etPassword
            it?.let { isValid -> passwordField.setValid(isValid)
                passwordField.setError(passwordField.subLayout.etInput.text?.isNotEmpty() == true && !isValid)}
        }
        viewModel.nameChange.observe(viewLifecycleOwner){
            val etName = viewBinding.etName
            it?.let { isValid -> etName.setValid(isValid)
                etName.setError(etName.subLayout.etInput.text?.isNotEmpty() == true && !isValid)}
        }

        viewModel.areFieldsValid.observe(viewLifecycleOwner){
            it.let {  viewBinding.btnReg.isEnabled = it == true }
        }
    }

    private fun setFieldValidations(registrationFragment: RegistrationFragment?) {
        val etEmail = viewBinding.etEmail.subLayout.etInput
        val etPassword = viewBinding.etPassword.subLayout.etInput
        val etName = viewBinding.etName.subLayout.etInput

        etEmail.addTextChangedListener(registrationFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etEmail,
                it
            )
        })
        etPassword.addTextChangedListener(registrationFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etPassword,
                it
            )
        })
        etName.addTextChangedListener(registrationFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etName,
                it
            )
        })
    }

    override fun onTextChanged(editable: Editable, taskyAppcompatEditText: TaskyAppCompatEditText) {
        if (taskyAppcompatEditText.id == R.id.et_email){
            viewModel.emailChange(editable.toString())
        }

        if(taskyAppcompatEditText.id == R.id.et_password){
            viewModel.passwordChange(editable.toString())
        }

        if(taskyAppcompatEditText.id == R.id.et_name){
            viewModel.nameChange(editable.toString())
        }
        viewModel.areFieldsValid()
    }

    private fun removeFieldValidations(registrationFragment: RegistrationFragment?) {
        val etEmail = viewBinding.etEmail.subLayout.etInput
        val etPassword = viewBinding.etPassword.subLayout.etInput
        val etName = viewBinding.etName.subLayout.etInput

        etEmail.removeTextChangedListener(registrationFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etEmail,
                it
            )
        })
        etPassword.removeTextChangedListener(registrationFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etPassword,
                it
            )
        })
        etName.removeTextChangedListener(registrationFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etName,
                it
            )
        })
    }

    override fun fieldsValidated(valid: Boolean) {
        viewBinding.btnReg.isEnabled = valid
        valid.let {
            if (it) {
                viewBinding.etEmail.clearFocus()
                viewBinding.etPassword.clearFocus()
                viewBinding.etName.clearFocus()
                viewBinding.btnReg.requestFocus()
            }
        }
    }

    private fun startLoginFragment() {
        (activity as EntryActivity).setTitle((activity as EntryActivity).getString(R.string.welcome_back))

        setFragmentManager(activity?.supportFragmentManager as FragmentManager)
        removeFragment(this)

        setFragmentManager(activity?.supportFragmentManager!!)
        val loginFragment = LoginFragment.getInstance()
        inflateFragment(loginFragment, R.id.fragment_container)
    }

    override fun onResume() {
        super.onResume()
        setFieldValidations(this)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        removeFieldValidations(this)
    }

    companion object {
        private lateinit var registrationFragment : RegistrationFragment
        @JvmStatic
        fun getInstance(): RegistrationFragment {
            registrationFragment = RegistrationFragment()
            registrationFragment.apply {
                enterTransition = Slide(Gravity.BOTTOM)
            }
            return registrationFragment
        }
    }
}