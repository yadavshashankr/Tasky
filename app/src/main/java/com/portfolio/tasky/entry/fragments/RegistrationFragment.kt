package com.portfolio.tasky.entry.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import com.portfolio.tasky.BaseFragment
import com.portfolio.tasky.databinding.LayoutRegistrationBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RegistrationFragment : BaseFragment() {
    private var viewBinding: LayoutRegistrationBinding? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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
            val loginFragment = LoginFragment.getInstance("test", "test")
            inflateFragment(loginFragment)
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(param1: String, param2: String): RegistrationFragment {
            val registrationFragment = RegistrationFragment()
            registrationFragment.apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
                enterTransition = Slide(Gravity.BOTTOM)
                exitTransition = Slide(Gravity.BOTTOM)
            }
            return registrationFragment
        }
    }
}