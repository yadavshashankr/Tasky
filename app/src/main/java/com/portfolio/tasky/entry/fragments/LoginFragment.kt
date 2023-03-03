package com.portfolio.tasky.entry.fragments

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.transition.Fade
import androidx.transition.Slide
import com.portfolio.tasky.*
import com.portfolio.tasky.databinding.LayoutLoginBinding
import com.portfolio.tasky.entry.EntryActivity
import com.portfolio.tasky.globals.Constants.Companion.Entry.LOGIN
import com.portfolio.tasky.globals.DataUtils.Companion.Validators.clearValidatorParams

class LoginFragment : Fragment(), FragmentInflater by FragmentInflaterImpl(), FieldsValidator {
    private lateinit var viewBinding: LayoutLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutLoginBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnLogin.setOnClickListener {
            openRegistrationFragment()
        }
    }
    private fun setFieldValidations(loginFragment: LoginFragment?) {
        clearValidatorParams()

        val etEmail = viewBinding.etEmail.subLayout.etInput
        etEmail.addTextChangedListener(loginFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etEmail, LOGIN,
                it
            )
        })

        val etPassword = viewBinding.etPassword.subLayout.etInput
        etPassword.addTextChangedListener(loginFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etPassword, LOGIN,
                it
            )
        })
    }

    private fun removeFieldValidations(loginFragment: LoginFragment?) {
        clearValidatorParams()

        val etEmail = viewBinding.etEmail.subLayout.etInput
        etEmail.removeTextChangedListener(loginFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etEmail, LOGIN,
                it
            )
        })

        val etPassword = viewBinding.etPassword.subLayout.etInput
        etPassword.removeTextChangedListener(loginFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etPassword, LOGIN,
                it
            )
        })
    }

    override fun fieldsValidated(valid: Boolean) {
        viewBinding.btnLogin.isEnabled = valid
        valid.let {
            if (it) {
                viewBinding.etEmail.clearFocus()
                viewBinding.etPassword.clearFocus()
                viewBinding.btnLogin.requestFocus()
            }
        }

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
        Log.d("CALL_BACK_REGISTERED_LOGIN", "TRUE")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        removeFieldValidations(this)
        Log.d("CALL_BACK_REGISTERED_LOGIN", "FALSE")
    }

    companion object {
        private var loginFragment : LoginFragment? = null
        @JvmStatic
        fun getInstance(): LoginFragment {
                if (loginFragment == null){
                    loginFragment = LoginFragment()
                }
                loginFragment?.apply {
                    enterTransition = Slide(Gravity.BOTTOM)
                    exitTransition = Fade(Fade.MODE_OUT)
                }
            return loginFragment as LoginFragment
        }

    }
}