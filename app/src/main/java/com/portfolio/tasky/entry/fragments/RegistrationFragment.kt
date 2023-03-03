package com.portfolio.tasky.entry.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.transition.Fade
import androidx.transition.Slide
import com.portfolio.tasky.*
import com.portfolio.tasky.databinding.LayoutRegistrationBinding
import com.portfolio.tasky.entry.EntryActivity
import com.portfolio.tasky.globals.Constants
import com.portfolio.tasky.globals.DataUtils.Companion.Validators.clearValidatorParams

class RegistrationFragment : Fragment(), FragmentInflater by FragmentInflaterImpl(), FieldsValidator {
    private lateinit var viewBinding: LayoutRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutRegistrationBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnReg.setOnClickListener {
            startLoginFragment()
        }
    }

    private fun setFieldValidations(registrationFragment: RegistrationFragment?) {
        clearValidatorParams()

        val etName = viewBinding.etName.subLayout.etInput
        etName.addTextChangedListener(registrationFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etName, Constants.Companion.Entry.REGISTRATION,
                it
            )
        })

        val etEmail = viewBinding.etEmail.subLayout.etInput
        etEmail.addTextChangedListener(registrationFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etEmail, Constants.Companion.Entry.REGISTRATION,
                it
            )
        })

        val etPassword = viewBinding.etPassword.subLayout.etInput
        etPassword.addTextChangedListener(registrationFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etPassword, Constants.Companion.Entry.REGISTRATION,
                it
            )
        })
    }

    private fun removeFieldValidations(registrationFragment: RegistrationFragment?) {
        clearValidatorParams()

        val etName = viewBinding.etName.subLayout.etInput
        etName.removeTextChangedListener(registrationFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etName, Constants.Companion.Entry.REGISTRATION,
                it
            )
        })

        val etEmail = viewBinding.etEmail.subLayout.etInput
        etEmail.removeTextChangedListener(registrationFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etEmail, Constants.Companion.Entry.REGISTRATION,
                it
            )
        })

        val etPassword = viewBinding.etPassword.subLayout.etInput
        etPassword.removeTextChangedListener(registrationFragment?.let {
            TaskyValidationWatcherImpl(viewBinding.etPassword, Constants.Companion.Entry.REGISTRATION,
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
        private var registrationFragment : RegistrationFragment? = null
        @JvmStatic
        fun getInstance(): RegistrationFragment {
            if (registrationFragment == null){
                registrationFragment = RegistrationFragment()
            }
            registrationFragment?.apply {
                enterTransition = Slide(Gravity.BOTTOM)
                exitTransition = Fade(Fade.MODE_OUT)
            }
            return registrationFragment as RegistrationFragment
        }
    }
}