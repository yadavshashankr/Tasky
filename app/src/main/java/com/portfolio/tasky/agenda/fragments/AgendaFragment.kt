package com.portfolio.tasky.agenda.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.transition.Slide
import com.portfolio.tasky.MainActivity
import com.portfolio.tasky.R
import com.portfolio.tasky.usecases.FragmentInflaterImpl
import com.portfolio.tasky.usecases.domain.FragmentInflater
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendaFragment : Fragment(), FragmentInflater by FragmentInflaterImpl() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_agenda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentManager(activity?.supportFragmentManager as FragmentManager)

        setToolbarAndFab()
    }

    private fun setToolbarAndFab() {
        val parentActivity = requireActivity() as MainActivity
        parentActivity.setTitle((activity as MainActivity).getString(R.string.create_your_account))

        parentActivity.showFAB(R.drawable.fab_plus, "agendaDialog")
        parentActivity.setFabLocation(true)
    }

    companion object {

        private lateinit var agendaFragment: AgendaFragment

        @JvmStatic
        fun getInstance(): AgendaFragment {
            agendaFragment = AgendaFragment()
            agendaFragment.apply {
                enterTransition = Slide(Gravity.BOTTOM)
            }
            return agendaFragment
        }
    }
}