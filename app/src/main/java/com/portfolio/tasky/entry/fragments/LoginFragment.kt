package com.portfolio.tasky.entry.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import com.portfolio.tasky.R
import com.portfolio.tasky.databinding.LayoutLoginBinding
import com.portfolio.tasky.entry.FragmentInflater
import com.portfolio.tasky.entry.FragmentInflaterImpl

class LoginFragment : Fragment(), FragmentInflater by FragmentInflaterImpl() {
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
            openRegistrationFragment()
        }
    }

    private fun openRegistrationFragment() {
        setFragmentManager(activity?.supportFragmentManager!!)
        val registrationFragment = RegistrationFragment.getInstance()
        inflateFragment(registrationFragment, R.id.fragment_container)
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