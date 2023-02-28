package com.portfolio.tasky.entry.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import com.portfolio.tasky.R
import com.portfolio.tasky.databinding.LayoutRegistrationBinding
import com.portfolio.tasky.entry.FragmentInflater
import com.portfolio.tasky.entry.FragmentInflaterImpl

class RegistrationFragment : Fragment(), FragmentInflater by FragmentInflaterImpl() {
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
            startLoginFragment()
        }
    }

    private fun startLoginFragment() {
        setFragmentManager(activity?.supportFragmentManager!!)
        val loginFragment = LoginFragment.getInstance()
        inflateFragment(loginFragment, R.id.fragment_container)
    }

    companion object {
        @JvmStatic
        fun getInstance(): RegistrationFragment {
            val registrationFragment = RegistrationFragment()
            registrationFragment.apply {
                enterTransition = Slide(Gravity.TOP)
                exitTransition = Slide(Gravity.TOP)
            }
            return registrationFragment
        }
    }
}