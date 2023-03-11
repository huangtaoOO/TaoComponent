package com.tao.bus.home

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.RouterURL
import com.example.base.base.BaseActivity
import com.example.bus.home.R
import com.example.bus.home.databinding.ActivityHomePageBinding
import com.example.lib_ktx.viewbinding.binding
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterURL.HOME_PAGE)
@AndroidEntryPoint
class HomePageActivity : BaseActivity() {

    private val binding: ActivityHomePageBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navView: BottomNavigationView = binding.homeNavView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}