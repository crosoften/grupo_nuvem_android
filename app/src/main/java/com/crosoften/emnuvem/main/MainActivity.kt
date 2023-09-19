package com.crosoften.emnuvem.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.crosoften.emnuvem.R
import com.crosoften.emnuvem.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigationWithNavController(binding.bottomNavView)
        handleBottomNavViewVisibility()
    }

    private fun setupBottomNavigationWithNavController(bottomNavigationView: BottomNavigationView) {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun showBottomMenu() {
        binding.bottomNavView.visibility = View.VISIBLE
    }

    private fun hideBottomMenu() {
        binding.bottomNavView.visibility = View.GONE
    }

    private fun handleBottomNavViewVisibility() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.camerasFragment -> {
                    showBottomMenu()
                }

                R.id.videosFragment -> {
                    showBottomMenu()
                }

                R.id.profileFragment -> {
                    showBottomMenu()
                }

                else -> {
                    hideBottomMenu()
                }
            }
        }
    }
}