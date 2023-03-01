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
import com.portfolio.tasky.databinding.LayoutRegistrationBinding
import com.portfolio.tasky.entry.EntryActivity
import com.portfolio.tasky.FragmentInflater
import com.portfolio.tasky.FragmentInflaterImpl

class RegistrationFragment : Fragment(), FragmentInflater by FragmentInflaterImpl() {
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

    private fun startLoginFragment() {
        (activity as EntryActivity).setTitle((activity as EntryActivity).getString(R.string.welcome_back))

        setFragmentManager(activity?.supportFragmentManager!!)
        val loginFragment = LoginFragment.getInstance()
        inflateFragment(loginFragment, R.id.fragment_container)
    }

    companion object {
        @JvmStatic
        fun getInstance(): RegistrationFragment {
            val registrationFragment = RegistrationFragment()
            registrationFragment.apply {
                enterTransition = Slide(Gravity.BOTTOM)
                exitTransition = Fade(Fade.MODE_OUT)
            }
            return registrationFragment
        }
    }
}