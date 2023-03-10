package com.portfolio.tasky.entry.fragments

import android.os.Bundle
import android.text.Editable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(), FragmentInflater by FragmentInflaterImpl(), TextChanged {
    private lateinit var viewBinding: LayoutLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }
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
            val email = viewBinding.etEmail.subLayout.etInput.text.toString()
            val password = viewBinding.etPassword.subLayout.etInput.text.toString()

            lifecycleScope.launch(Dispatchers.IO + coroutineExceptionHandler){
                viewModel.makeLoginCall(AuthenticationRequest(email, password))
            }

            viewModel.loginObserver.observe(viewLifecycleOwner){
                if(it != null){
                    Toast.makeText(activity, "Token ${it.token}", Toast.LENGTH_SHORT).show()
                }
            }
        }
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
    /**
     * Necessary for navigating to RegistrationFragment.
     * Currently not called
     */
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