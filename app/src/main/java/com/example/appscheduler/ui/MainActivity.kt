package com.example.appscheduler.ui

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.appscheduler.R
import com.example.appscheduler.databinding.ActivityMainBinding
import com.example.appscheduler.utils.ExitProgressDialog
import com.example.appscheduler.utils.WANT_TO_EXIT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbarMain)

        sharedPreference = getSharedPreferences("key", MODE_PRIVATE)

        setUpNav()
    }

    private fun setUpNav() {
        val navHostContainer = supportFragmentManager.findFragmentById(
            R.id.navHostContainer
        ) as NavHostFragment

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment
            )
        )

        navController = navHostContainer.navController
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.exit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exitFromApp -> {
                exitFromApp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private suspend fun clearAndExit(){
        sharedPreference.edit().clear().apply()

        lifecycleScope.launch {
            delay(600)
            exitProcess(0)
        }
    }

    private fun exitFromApp() {
        AlertDialog.Builder(this, R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog)
            .setTitle(WANT_TO_EXIT)
            .setIcon(R.drawable.exit_run)
            .setCancelable(false)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ ->
                val exitProgress = ExitProgressDialog.progressDialog(this)
                exitProgress.show()
                lifecycleScope.launch {
                    delay(900)
                    exitProgress.dismiss()
                    clearAndExit()
                }
            }.create().show()
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.homeFragment) {
            exitFromApp()
        } else {
            super.onBackPressed()
        }
    }
}