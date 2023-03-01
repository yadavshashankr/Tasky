package com.portfolio.tasky.entry.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Slide
import com.portfolio.tasky.R
import com.portfolio.tasky.databinding.LayoutLoginBinding
import com.portfolio.tasky.entry.EntryActivity
import com.portfolio.tasky.FragmentInflater
import com.portfolio.tasky.FragmentInflaterImpl

class LoginFragment : Fragment(), FragmentInflater by FragmentInflaterImpl() {
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