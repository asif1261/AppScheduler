package com.example.appscheduler.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appscheduler.R
import com.example.appscheduler.data.AppInfo
import com.example.appscheduler.databinding.FragmentHomeBinding
import com.example.appscheduler.ui.adapters.ActionClickListenerForInstalledApps
import com.example.appscheduler.ui.adapters.InstalledAppsAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.transition.MaterialFadeThrough
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), ActionClickListenerForInstalledApps {

    private lateinit var navController: NavController
    private var currentBinding: FragmentHomeBinding? = null
    private val binding get() = currentBinding!!

    private lateinit var installedAppAdapter: InstalledAppsAdapter
    private lateinit var sharedPreference: SharedPreferences
    private var appsList: MutableList<AppInfo>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentBinding = FragmentHomeBinding.bind(view)
        showAnimation()
        navController = findNavController()
        sharedPreference = requireContext().getSharedPreferences("key", Context.MODE_PRIVATE)

        bottomSheetBehavior()
        initRecyclerView()
        getTheInstalledAppsList()
    }

    private fun bottomSheetBehavior(){
        binding.apply {
            BottomSheetBehavior.from(layoutSelectApps).apply {
                peekHeight = 250
                this.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    private fun initRecyclerView(){
        installedAppAdapter = InstalledAppsAdapter(this)
        binding.apply {
            rvSelectApp.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = installedAppAdapter
            }
        }
    }

    private fun getTheInstalledAppsList() {
        viewLifecycleOwner.lifecycleScope.launch {
            val packageManager = requireContext().packageManager
            val appsData = withContext(Dispatchers.IO) {
                packageManager.getInstalledPackages(0) //.filterNot { it.packageName.startsWith("com.") }
                    .map {
                        AppInfo(
                            it.applicationInfo.loadLabel(packageManager).toString(),
                            it.packageName,
                            it.versionName,
                            it.versionCode,
                            it.applicationInfo.loadIcon(packageManager)
                        )
                    }
            }

            if (appsData != null) {
                appsList = appsData.toMutableList()
                installedAppAdapter.submitList(appsData)
                showRecyclerViewSelectApps(true)
                showNoAppsLayoutSelectApps(false)
            } else {
                showRecyclerViewSelectApps(false)
                showNoAppsLayoutSelectApps(true)
            }
        }
    }

    private fun dataForActionListener(position: Int, key: String){
        val subsetLeaves: AppInfo = appsList!![position]

        val prefsEditor = sharedPreference.edit()
        val gson = Gson()
        val json = gson.toJson(subsetLeaves)
        prefsEditor.putString(key, json)
        prefsEditor.apply()
    }

    override fun actionClickListener(position: Int) {
        goToSetSchedule(position)
    }

    private fun goToSetSchedule(position: Int){
        dataForActionListener(position, "installed_appData")
        val action = HomeFragmentDirections.actionHomeFragmentToSetScheduleFragment("Set Schedule")
        navController.navigate(action)
    }

    private fun goToEditSchedule(){
        val action = HomeFragmentDirections.actionHomeFragmentToEditScheduleFragment("Edit Schedule")
        navController.navigate(action)
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

    private fun showRecyclerViewSelectApps(boolean: Boolean){
        binding.rvSelectApp.isVisible = boolean
    }

    private fun showNoAppsLayoutSelectApps(boolean: Boolean){
        binding.tvNoAppsSelectApp.isVisible = boolean
    }
}