package com.exraion.beu.ui.main

import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.exraion.beu.R
import com.exraion.beu.base.BaseActivity
import com.exraion.beu.common.deselectAllItems
import com.exraion.beu.databinding.ActivityMainBinding
import com.exraion.beu.util.ScreenOrientation

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun inflateViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation? {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun ActivityMainBinding.binder() {
    
        setUpNavigation()
        
        navView.itemIconTintList = null
        
        fabImageRecognition.setOnClickListener {
            Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment_activity_base)
                .navigate(R.id.navigation_image_recognition)
           navView.deselectAllItems()
        }
        
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment_activity_base)
                        .navigate(R.id.navigation_home)
                }
                R.id.navigation_voucher -> {
                    Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment_activity_base)
                        .navigate(R.id.navigation_voucher)
                }
                R.id.navigation_leaderboard -> {
                    Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment_activity_base)
                        .navigate(R.id.navigation_leaderboard)
                }
                R.id.navigation_profile -> {
                    Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment_activity_base)
                        .navigate(R.id.navigation_profile)
                }
            }
            true
        }
    }
    
    private fun setUpNavigation() {
        val navController = findNavController(R.id.nav_host_fragment_activity_base)
        binding.navView.setupWithNavController(navController)
        binding.navView.menu.findItem(R.id.navigation_placeholder).isEnabled = false
    }
    
}