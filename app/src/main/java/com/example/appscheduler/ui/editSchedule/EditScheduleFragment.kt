package com.example.appscheduler.ui.editSchedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.appscheduler.R
import com.example.appscheduler.databinding.FragmentEditScheduleBinding
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditScheduleFragment : Fragment(R.layout.fragment_set_schedule) {

    private lateinit var navController: NavController
    private var currentBinding: FragmentEditScheduleBinding? = null
    private val binding get() = currentBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        currentBinding = FragmentEditScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentBinding = FragmentEditScheduleBinding.bind(view)
        showAnimation()
        navController = findNavController()
    }

    private fun showAnimation(){
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }

    override fun onResume() {
        super.onResume()
        showAnimation()
    }

    override fun onStop() {
        super.onStop()
        showAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        currentBinding = null
    }
}