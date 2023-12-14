package com.example.batterymanagerpractice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.batterymanagerpractice.data.BatteryManagerBroadcastReceiver
import com.example.batterymanagerpractice.data.BatteryUtil
import com.example.batterymanagerpractice.data.MyAndroidViewModel
import com.example.batterymanagerpractice.data.MyAndroidViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Date

class BatteryManagerFragment : Fragment() {

    // Initialize Views
    private lateinit var tv_timestamp : TextView
    private lateinit var iv_icon: ImageView
    private lateinit var tv_health : TextView
    private lateinit var tv_status : TextView
    private lateinit var tv_level_scale : TextView
    private lateinit var tv_plugged : TextView
    private lateinit var tv_present : TextView
    private lateinit var tv_technology : TextView
    private lateinit var tv_batterylow : TextView
    private lateinit var tv_voltage : TextView
    private lateinit var tv_temperature : TextView
    private lateinit var tv_property_capacity : TextView
    private lateinit var tv_property_charge_count : TextView
    private lateinit var tv_property_energy_counter : TextView
    private lateinit var tv_property_current_average : TextView
    private lateinit var tv_property_current_now :TextView
    private lateinit var tv_property_status : TextView
    private lateinit var tv_isCharging : TextView
    private lateinit var tv_charge_remaining : TextView

    private lateinit var bt_toView : FloatingActionButton


    // Initialize ViewModel
    private val viewModel: MyAndroidViewModel by activityViewModels{
        MyAndroidViewModelFactory(
            (activity?.application as MyAndroidApplication).database.dao()
        )
    }

    // BatteryManager Variables
    private lateinit var broadcastReceiver: BroadcastReceiver
    private lateinit var batteryManager: BatteryManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize the BatteryManager within onAttach using the context
        batteryManager = context.getSystemService(BATTERY_SERVICE) as BatteryManager
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_battery_manager, container, false)

        // Initialize Views
        bt_toView = layout.findViewById(R.id.bt_addBatteryInfo)

        tv_timestamp = layout.findViewById(R.id.text_timestamp)
        iv_icon = layout.findViewById(R.id.image_icon_small)
        tv_health = layout.findViewById(R.id.text_health)
        tv_status = layout.findViewById(R.id.text_status)
        tv_level_scale = layout.findViewById(R.id.text_level_scale)
        tv_plugged = layout.findViewById(R.id.text_plugged)
        tv_present = layout.findViewById(R.id.text_present)
        tv_technology = layout.findViewById(R.id.text_technology)
        tv_batterylow = layout.findViewById(R.id.text_battery_low)
        tv_voltage = layout.findViewById(R.id.text_voltage)
        tv_temperature = layout.findViewById(R.id.text_temperature)
        tv_property_capacity = layout.findViewById(R.id.text_property_capacity)
        tv_property_charge_count = layout.findViewById(R.id.text_property_charge_counter)
        tv_property_energy_counter = layout.findViewById(R.id.text_property_energy_counter)
        tv_property_current_average = layout.findViewById(R.id.text_property_current_average)
        tv_property_current_now = layout.findViewById(R.id.text_property_current_now)
        tv_property_status = layout.findViewById(R.id.text_property_status)
        tv_isCharging = layout.findViewById(R.id.text_is_charging)
        tv_charge_remaining = layout.findViewById(R.id.text_charge_time_remaining)


        // BroadcastReceiver Listener
        broadcastReceiver = BatteryManagerBroadcastReceiver {
            val propertyCapacity =
                batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            val propertyChargeCounter =
                batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER)
            val propertyEnergyCounter =
                batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER)
            val propertyCurrentAverage =
                batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE)
            val propertyCurrentNow =
                batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)
            var propertyStatus: Int? = null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                propertyStatus = batteryManager.getIntProperty(
                    BatteryManager.BATTERY_PROPERTY_STATUS
                )
            }

            var isCharging: Boolean? = null
            var chargeTimeRemaining: Long? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                isCharging = batteryManager.isCharging
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                chargeTimeRemaining = batteryManager.computeChargeTimeRemaining()
            }

            val iconSmall = it.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, -99)
            val health = it.getIntExtra(BatteryManager.EXTRA_HEALTH, -99)
            val status = it.getIntExtra(BatteryManager.EXTRA_STATUS, -99)
            val level = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -99)
            val scale = it.getIntExtra(BatteryManager.EXTRA_SCALE, -99)
            val plugged = it.getIntExtra(BatteryManager.EXTRA_PLUGGED, -99)
            val present = it.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false)
            val technology = it.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)
            val batteryLow = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                it.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW, false)
            } else {
                null
            }
            val voltage = it.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -99)
            val temperature = it.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -99)

            // Update ViewModel
            MyAndroidViewModel.battery_timestamp = Date().toString()
            MyAndroidViewModel.battery_health = BatteryUtil.getBatteryHealthText(health)
            MyAndroidViewModel.battery_status = BatteryUtil.getBatteryStatusText(status)
            MyAndroidViewModel.battery_level = level.toString()
            MyAndroidViewModel.battery_scale = scale.toString()
            MyAndroidViewModel.battery_plugged = BatteryUtil.getBatteryPluggedText(plugged)
            MyAndroidViewModel.battery_present = present.toString()
            MyAndroidViewModel.battery_technology = technology ?: "Unknown"
            MyAndroidViewModel.battery_batteryLow = batteryLow?.toString() ?: "Unknown"
            MyAndroidViewModel.battery_voltage = voltage.toString()
            MyAndroidViewModel.battery_temperature = temperature.toString()

            MyAndroidViewModel.battery_propertyCapacity = propertyCapacity.toString()
            MyAndroidViewModel.battery_propertyChargeCounter = "${propertyChargeCounter} µAh"
            MyAndroidViewModel.battery_propertyEnergyCounter = "${propertyEnergyCounter} nWh"
            MyAndroidViewModel.battery_propertyCurrentAverage = "${propertyCurrentAverage} µA"
            MyAndroidViewModel.battery_propertyCurrentNow = "${propertyCurrentNow} µA"
            MyAndroidViewModel.battery_propertyStatus =
                BatteryUtil.getBatteryStatusText(propertyStatus ?: -99)

            MyAndroidViewModel.battery_isCharging =
                isCharging?.toString() ?: "-"
            MyAndroidViewModel.battery_chargeTimeRemaining =
                if (chargeTimeRemaining != null) "${chargeTimeRemaining} ms" else "-"


            // Update Views
            tv_timestamp.text = MyAndroidViewModel.battery_timestamp
            tv_health.text = MyAndroidViewModel.battery_health
            tv_status.text = MyAndroidViewModel.battery_status
            tv_level_scale.text = "${MyAndroidViewModel.battery_level} / ${MyAndroidViewModel.battery_scale}"
            tv_plugged.text = MyAndroidViewModel.battery_plugged
            tv_present.text = MyAndroidViewModel.battery_present
            tv_technology.text = MyAndroidViewModel.battery_technology
            tv_batterylow.text = MyAndroidViewModel.battery_batteryLow
            tv_voltage.text = MyAndroidViewModel.battery_voltage
            tv_temperature.text = MyAndroidViewModel.battery_temperature

            tv_property_capacity.text = MyAndroidViewModel.battery_propertyCapacity
            tv_property_charge_count.text = MyAndroidViewModel.battery_propertyChargeCounter
            tv_property_energy_counter.text = MyAndroidViewModel.battery_propertyEnergyCounter
            tv_property_current_average.text = MyAndroidViewModel.battery_propertyCurrentAverage
            tv_property_current_now.text = MyAndroidViewModel.battery_propertyCurrentNow
            tv_property_status.text =
                MyAndroidViewModel.battery_propertyStatus

            tv_isCharging.text =
                MyAndroidViewModel.battery_isCharging
            tv_charge_remaining.text =
                MyAndroidViewModel.battery_chargeTimeRemaining

        }

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_CHANGED)
        }
        requireContext().registerReceiver(broadcastReceiver, filter)



        // Add Listeners
        bt_toView.setOnClickListener {
            addNewBatteryData()
        }


        return layout
    }

    private fun addNewBatteryData(){
        viewModel.addBatteryData(
            timestamp = tv_timestamp.text.toString(),
            health = tv_health.text.toString(),
            status = tv_status.text.toString(),
            voltage = tv_voltage.text.toString(),
            temperature = tv_temperature.text.toString(),
            technology = tv_technology.text.toString(),
            level = MyAndroidViewModel.battery_level,
            scale = MyAndroidViewModel.battery_scale,
            present = tv_present.text.toString(),
            batteryLow = tv_batterylow.text.toString(),
            plugged = tv_plugged.text.toString(),
            propertyCapacity = tv_property_capacity.text.toString(),
            propertyChargeCounter = tv_property_charge_count.text.toString(),
            propertyCurrentAverage = tv_property_current_average.text.toString(),
            propertyCurrentNow = tv_property_current_now.text.toString(),
            propertyEnergyCounter = tv_property_energy_counter.text.toString(),
            propertyStatus = tv_property_status.text.toString(),
            isCharging = tv_isCharging.text.toString(),
            chargeTimeRemaining = tv_charge_remaining.text.toString()
        )

        findNavController().navigate(R.id.action_batteryManagerFragment_to_viewBatteryDataFragment)
    }

    override fun onDestroy() {
        super.onDestroy()

        requireContext().unregisterReceiver(broadcastReceiver)
    }


}