package com.portfolio.tasky.entry.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.Slide
import com.portfolio.tasky.BaseFragment
import com.portfolio.tasky.databinding.LayoutRegistrationBinding

class RegistrationFragment : BaseFragment() {
    private var viewBinding: LayoutRegistrationBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutRegistrationBinding.inflate(inflater, container, false)
        return viewBinding?.root as View
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.tvReg?.setOnClickListener {
            val loginFragment = LoginFragment.getInstance()
            inflateFragment(loginFragment)
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(): RegistrationFragment {
            val registrationFragment = RegistrationFragment()
            registrationFragment.apply {
                enterTransition = Slide(Gravity.BOTTOM)
                exitTransition = Slide(Gravity.BOTTOM)
            }
            return registrationFragment
        }
    }
}