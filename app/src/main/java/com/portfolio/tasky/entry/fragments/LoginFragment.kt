package com.portfolio.tasky.entry.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.transition.Slide
import com.portfolio.tasky.*
import com.portfolio.tasky.databinding.LayoutLoginBinding
import com.portfolio.tasky.entry.EntryActivity
import com.portfolio.tasky.entry.viewModels.LoginViewModel
import com.portfolio.tasky.entry.models.AuthenticationRequest
import com.portfolio.tasky.usecases.domain.FragmentInflater
import com.portfolio.tasky.usecases.FragmentInflaterImpl
import com.portfolio.tasky.usecases.NetworkStatus
import com.portfolio.tasky.usecases.TaskyWatcherImpl
import com.portfolio.tasky.usecases.domain.TextChanged
import com.portfolio.tasky.views.TaskyAppCompatEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), FragmentInflater by FragmentInflaterImpl(), TextChanged, OnClickListener {
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

        setToolbarAndFab()
        setObservers()

        viewBinding.btnLogin.setOnClickListener(this)
        viewBinding.tvRegister.setOnClickListener(this)
    }

    private fun setToolbarAndFab() {
        setFragmentManager(activity?.supportFragmentManager as FragmentManager)
        val parentActivity = requireActivity() as EntryActivity
        parentActivity.setTitle(requireContext().getString(R.string.welcome_back))
        parentActivity.hideFAB()
    }

    private fun setObservers() {
        viewModel.email.observe(viewLifecycleOwner) {
            val emailField = viewBinding.etEmail
            it?.let { isValid -> emailField.setValid(isValid)
                emailField.setError(emailField.subLayout.etInput.text?.isNotEmpty() == true && !isValid)}
        }

        viewModel.password.observe(viewLifecycleOwner) {
            val passwordField = viewBinding.etPassword
            it?.let { isValid -> passwordField.setValid(isValid)
                passwordField.setError(passwordField.subLayout.etInput.text?.isNotEmpty() == true && !isValid)}
        }

        viewModel.validateFields.observe(viewLifecycleOwner) {
            it.let {  viewBinding.btnLogin.isEnabled = it == true }
        }

        viewModel.networkObserver.observe(viewLifecycleOwner){
            when(it){
                NetworkStatus.Available -> {
                    viewBinding.btnLogin.isEnabled = true
                }
                NetworkStatus.Unavailable -> {
                    viewBinding.btnLogin.isEnabled = false
                }
            }
        }
    }

    private fun setFieldValidations(loginFragment: LoginFragment?) {
        val etEmail = viewBinding.etEmail.subLayout.etInput
        val etPassword = viewBinding.etPassword.subLayout.etInput

        etEmail.addTextChangedListener(loginFragment?.let {
            TaskyWatcherImpl(viewBinding.etEmail,
                it
            )
        })
        etPassword.addTextChangedListener(loginFragment?.let {
            TaskyWatcherImpl(viewBinding.etPassword,
                it
            )
        })
    }

    override fun onTextChanged(editable: Editable, taskyAppcompatEditText: TaskyAppCompatEditText) {
        if (taskyAppcompatEditText.id == R.id.et_email) {
            viewModel.onEmailChange(editable.toString())
        }

        if(taskyAppcompatEditText.id == R.id.et_password) {
            viewModel.onPasswordChange(editable.toString())
        }
            viewModel.areFieldsValid()
    }

    private fun removeFieldValidations(loginFragment: LoginFragment) {

        val etEmail = viewBinding.etEmail.subLayout.etInput
        val etPassword = viewBinding.etPassword.subLayout.etInput

        etEmail.removeTextChangedListener(
            TaskyWatcherImpl(viewBinding.etEmail,
                loginFragment
            )
        )

        etPassword.removeTextChangedListener(
            TaskyWatcherImpl(viewBinding.etPassword,
                loginFragment
            )
        )
    }

    private fun startLogin() {
        val email = viewBinding.etEmail.subLayout.etInput.text.toString()
        val password = viewBinding.etPassword.subLayout.etInput.text.toString()

        viewModel.login(AuthenticationRequest(email, password))

        viewModel.loginObserver.observe(viewLifecycleOwner){
           Log.v("Login is ", "SUCCESS")
        }
    }

    private fun openRegistrationFragment() {
        setFragmentManager(activity?.supportFragmentManager as FragmentManager)
        val parentActivity = requireActivity() as EntryActivity
        parentActivity.startFragment(RegistrationFragment.getInstance())
        removeFragment(this)
    }

    override fun onResume() {
        super.onResume()
        setFieldValidations(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeFieldValidations(this)
    }

    override fun onClick(view: View?) {
        when(view){
            viewBinding.tvRegister -> openRegistrationFragment()

            viewBinding.btnLogin ->  startLogin()
        }
    }


    companion object {
        private lateinit var loginFragment : LoginFragment
        @JvmStatic
        fun getInstance(): LoginFragment {
            loginFragment = LoginFragment()
            loginFragment.apply {
                enterTransition = Slide(Gravity.BOTTOM)
            }
            return loginFragment
        }

    }
}