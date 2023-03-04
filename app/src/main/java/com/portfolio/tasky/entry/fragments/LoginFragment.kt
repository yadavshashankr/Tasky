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
import com.portfolio.tasky.databinding.LayoutLoginBinding
import com.portfolio.tasky.entry.EntryActivity
import com.portfolio.tasky.viewModels.LoginViewModel
import com.portfolio.tasky.views.TaskyAppCompatEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), FragmentInflater by FragmentInflaterImpl(), TextChanged  {
    private lateinit var viewBinding: LayoutLoginBinding

    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.layout_login, container, false)
        viewBinding.lifecycleOwner = this
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        viewBinding.btnLogin.setOnClickListener {
            openRegistrationFragment()
        }
    }

    private fun setObservers() {
        viewModel.emailChange.observe(viewLifecycleOwner){
            val emailField = viewBinding.etEmail
            it?.let { it1 -> emailField.setValid(it1)
                emailField.setError(emailField.subLayout.etInput.text?.isNotEmpty() as Boolean && !it1)}
        }
        viewModel.passwordChange.observe(viewLifecycleOwner){
            val passwordField = viewBinding.etPassword
            it?.let { it1 -> passwordField.setValid(it1)
                passwordField.setError(passwordField.subLayout.etInput.text?.isNotEmpty() as Boolean && !it1)}
        }
        viewModel.areFieldsValid.observe(viewLifecycleOwner){
            it.let {  viewBinding.btnLogin.isEnabled = it == true }
        }
    }

    private fun setFieldValidations(loginFragment: LoginFragment?) {
        val etEmail = viewBinding.etEmail.subLayout.etInput
        val etPassword = viewBinding.etPassword.subLayout.etInput

        etEmail.addTextChangedListener(loginFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etEmail,
                it
            )
        })
        etPassword.addTextChangedListener(loginFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etPassword,
                it
            )
        })
    }

    override fun onTextChanged(editable: Editable, taskyAppcompatEditText: TaskyAppCompatEditText) {
        if (taskyAppcompatEditText.id == R.id.et_email){
            viewModel.emailChange(editable.toString() as CharSequence)
        }


        if(taskyAppcompatEditText.id == R.id.et_password){
            viewModel.passwordChange(editable.toString() as CharSequence)
        }
            viewModel.areFieldsValid()
    }

    private fun removeFieldValidations(loginFragment: LoginFragment?) {

        val etEmail = viewBinding.etEmail.subLayout.etInput
        val etPassword = viewBinding.etPassword.subLayout.etInput

        etEmail.removeTextChangedListener(loginFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etEmail,
                it
            )
        })

        etPassword.removeTextChangedListener(loginFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etPassword,
                it
            )
        })
    }

    private fun openRegistrationFragment() {
        (activity as EntryActivity).setTitle((activity as EntryActivity).getString(R.string.create_your_account))

        setFragmentManager(activity?.supportFragmentManager as FragmentManager)
        removeFragment(this)

        val registrationFragment = RegistrationFragment.getInstance()
        inflateFragment(registrationFragment, R.id.fragment_container)
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
        private var loginFragment : LoginFragment? = null
        @JvmStatic
        fun getInstance(): LoginFragment {
            loginFragment = LoginFragment()
            loginFragment?.apply {
                enterTransition = Slide(Gravity.BOTTOM)
            }
            return loginFragment as LoginFragment
        }

    }
}