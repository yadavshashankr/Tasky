package com.portfolio.tasky.entry.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        setFieldValidations()

        viewBinding.btnLogin.setOnClickListener {
            openRegistrationFragment()
        }
    }
    private fun setFieldValidations() {
        clearValidatorParams()

        val etEmail = viewBinding.etEmail.subLayout.etInput
        etEmail.addTextChangedListener(TaskyValidationWatcherImpl(viewBinding.etEmail, LOGIN, this))

        val etPassword = viewBinding.etPassword.subLayout.etInput
        etPassword.addTextChangedListener(TaskyValidationWatcherImpl(viewBinding.etPassword, LOGIN, this))
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

        setFragmentManager(activity?.supportFragmentManager!!)
        val registrationFragment = RegistrationFragment.getInstance()
        inflateFragment(registrationFragment, R.id.fragment_container)
    }

    companion object {
        @JvmStatic
        fun getInstance(): LoginFragment {
            val loginFragment = LoginFragment()
            loginFragment.apply {
                enterTransition = Slide(Gravity.BOTTOM)
                exitTransition = Fade(Fade.MODE_OUT)
            }
            return loginFragment
        }

    }
}