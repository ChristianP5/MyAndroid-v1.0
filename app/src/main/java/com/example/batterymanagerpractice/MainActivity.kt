package com.example.batterymanagerpractice

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.batterymanagerpractice.data.MyAndroidViewModel
import com.example.batterymanagerpractice.data.MyAndroidViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var bt_nav_view:Button
    lateinit var bt_nav_battery:Button
    lateinit var bt_nav_connectivity:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Views
        bt_nav_view = findViewById(R.id.bt_nav_view)
        bt_nav_battery = findViewById(R.id.bt_nav_battery)
        bt_nav_connectivity = findViewById(R.id.bt_nav_connectivity)


        // Add Listeners
        bt_nav_view.setOnClickListener {
            navigateToFragment(R.id.viewFragment)
        }

        bt_nav_battery.setOnClickListener {
            navigateToFragment(R.id.batteryManagerFragment)
        }

        bt_nav_connectivity.setOnClickListener {
            navigateToFragment(R.id.connectivityManagerFragment)
        }
    }

    fun navigateToFragment(destinationId: Int) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nhf_activity) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(destinationId)
    }
}