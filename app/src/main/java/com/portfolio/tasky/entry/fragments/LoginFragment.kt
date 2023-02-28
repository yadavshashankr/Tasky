package com.portfolio.tasky.entry.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.Slide
import com.portfolio.tasky.BaseFragment
import com.portfolio.tasky.databinding.LayoutLoginBinding

class LoginFragment : BaseFragment() {
    private var viewBinding: LayoutLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LayoutLoginBinding.inflate(inflater, container, false)
        return viewBinding?.root as View
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.tvLog?.setOnClickListener {
            val loginFragment = RegistrationFragment.getInstance()
            inflateFragment(loginFragment)
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(): LoginFragment {
            val loginFragment = LoginFragment()
            loginFragment.apply {
                enterTransition = Slide(Gravity.TOP)
                exitTransition = Slide(Gravity.TOP)
            }
            return loginFragment
        }

    }
}