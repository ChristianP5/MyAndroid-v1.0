package com.example.batterymanagerpractice.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.Date

class MyAndroidViewModel(private val dao: MainDAO): ViewModel() {


    companion object {
        // Battery Manager Variables
        var battery_timestamp: String = ""
        var battery_action: String = ""
        var battery_health: String = "0"
        var battery_status: String = "0"
        var battery_voltage: String = "0"
        var battery_temperature: String = "0"
        var battery_technology: String? = null
        var battery_level: String = "0"
        var battery_scale: String = "0"
        var battery_present: String = "false"
        var battery_batteryLow: String? = null
        var battery_plugged: String = "0"
        var battery_propertyCapacity: String = "0"
        var battery_propertyChargeCounter: String = "0"
        var battery_propertyCurrentAverage: String = "0"
        var battery_propertyCurrentNow: String = "0"
        var battery_propertyEnergyCounter: String = "0"
        var battery_propertyStatus: String? = null
        var battery_isCharging: String? = null
        var battery_chargeTimeRemaining: String? = null

        // Connectivity Manager Variables
        var networkInfo_timestamp: String? = ""
        var networkInfo_isMetered: String? = null
        var networkInfo_type: String? = null
        var networkInfo_subType: String? = null
        var networkInfo_state: String? = null
        var networkInfo_detailedState: String? = null
        var networkInfo_isRoaming: String? = null
        var networkInfo_networkID: String? = null

        var networkInfo_wifi_signalStrength: Int? = 0
        var networkInfo_wifi_ssid: String? = null
        var networkInfo_wifi_isHiddenSSID: Boolean? = false
        var networkInfo_wifi_bssid: String? = null
        var networkInfo_wifi_linkSpeed: Double? = 0.0
        var networkInfo_wifi_frequency: Double? = 0.0
        var networkInfo_wifi_macAddress: String? = null

        var networkInfo_telephony_networkType: String? = null
        var networkInfo_telephony_simSerialNumber: String? = null
        var networkInfo_telephony_imei: String? = null
        var networkInfo_telephony_gsmSignalStrength: Double? = 0.0

    }

    // For Connectivity Data DAO

    private fun insertConnectivityInfo(connectivityData: ConnectivityData){
        viewModelScope.launch {
            dao.insertConnectivityData(connectivityData)
        }
    }

    private fun getNewConnectivityInfoEntry(
        timestamp: String,
        connectivity_isMetered: String,
        connectivity_type: String,
        connectivity_subtype: String,
        connectivity_state: String,
        connectivity_extraInfo: String,
        connectivity_detailedState: String,
        connectivity_isRoaming: String,
        connectivity_networkID: String,
        wifi_signalStrength: Int,
        wifi_ssid: String,
        wifi_isHiddenSSID: Boolean,
        wifi_bssid: String,
        wifi_linkSpeed: Double,
        wifi_frequency: Double,
        wifi_macAddress: String,
        telephony_networkType: String,
        telephony_sim: String,
        telephony_imei: String,
        telephony_gsmSignalStrength: String,
    ): ConnectivityData{
        return ConnectivityData(
            id = 0,
            timestamp = Date().toString(),
            connectivity_isMetered = connectivity_isMetered,
            connectivity_type = connectivity_type,
            connectivity_subtype = connectivity_subtype,
            connectivity_state = connectivity_state,
            connectivity_extraInfo = connectivity_extraInfo,
            connectivity_detailedState = connectivity_detailedState,
            connectivity_isRoaming = connectivity_isRoaming,
            connectivity_networkID = connectivity_networkID,
            wifi_signalStrength = wifi_signalStrength,
            wifi_ssid = wifi_ssid,
            wifi_isHiddenSSID = wifi_isHiddenSSID,
            wifi_bssid = wifi_bssid,
            wifi_linkSpeed = wifi_linkSpeed,
            wifi_frequency = wifi_frequency,
            wifi_macAddress = wifi_macAddress,
            telephony_networkType = telephony_networkType,
            telephony_sim = telephony_sim,
            telephony_imei = telephony_imei,
            telephony_gsmSignalStrength = telephony_gsmSignalStrength
        )
    }

    fun addConnectivityData(
        timestamp: String,
        connectivity_isMetered: String,
        connectivity_type: String,
        connectivity_subtype: String,
        connectivity_state: String,
        connectivity_extraInfo: String,
        connectivity_detailedState: String,
        connectivity_isRoaming: String,
        connectivity_networkID: String,
        wifi_signalStrength: Int,
        wifi_ssid: String,
        wifi_isHiddenSSID: Boolean,
        wifi_bssid: String,
        wifi_linkSpeed: Double,
        wifi_frequency: Double,
        wifi_macAddress: String,
        telephony_networkType: String,
        telephony_sim: String,
        telephony_imei: String,
        telephony_gsmSignalStrength: String,
    ){
        val newConnectivityData = getNewConnectivityInfoEntry(
            timestamp = Date().toString(),
            connectivity_isMetered = connectivity_isMetered,
            connectivity_type = connectivity_type,
            connectivity_subtype = connectivity_subtype,
            connectivity_state = connectivity_state,
            connectivity_extraInfo = connectivity_extraInfo,
            connectivity_detailedState = connectivity_detailedState,
            connectivity_isRoaming = connectivity_isRoaming,
            connectivity_networkID = connectivity_networkID,
            wifi_signalStrength = wifi_signalStrength,
            wifi_ssid = wifi_ssid,
            wifi_isHiddenSSID = wifi_isHiddenSSID,
            wifi_bssid = wifi_bssid,
            wifi_linkSpeed = wifi_linkSpeed,
            wifi_frequency = wifi_frequency,
            wifi_macAddress = wifi_macAddress,
            telephony_networkType = telephony_networkType,
            telephony_sim = telephony_sim,
            telephony_imei = telephony_imei,
            telephony_gsmSignalStrength = telephony_gsmSignalStrength
        )

        insertConnectivityInfo(newConnectivityData)
    }

    fun getAllConnectivityData(): LiveData<List<ConnectivityData>>{
        return dao.getAllConnectivityInfo()
    }

    // For Battery Data DAO

    private fun insertBatteryInfo(batteryData: BatteryData){
        viewModelScope.launch {
            dao.insertBatteryData(batteryData)
        }
    }



    private fun getNewBatteryInfoEntry(
        timestamp: String,
        action: String = "",
        health: String = "0",
        status: String = "0",
        voltage: String = "0",
        temperature: String = "0",
        technology: String? = null,
        level: String = "0",
        scale: String = "0",
        present: String = "false",
        batteryLow: String,
        plugged: String = "0",
        propertyCapacity: String = "0",
        propertyChargeCounter: String = "0",
        propertyCurrentAverage: String = "0",
        propertyCurrentNow: String = "0",
        propertyEnergyCounter: String = "0",
        propertyStatus: String? = null,
        isCharging: String? = null,
        chargeTimeRemaining: String? = null
    ): BatteryData {
        return BatteryData(
            id = 0,
            timestamp = Date().toString(),
            action = action,
            health = health,
            status = status,
            voltage = voltage.toIntOrNull() ?: 0,
            temperature = temperature.toIntOrNull() ?: 0,
            technology = technology,
            level = level,
            scale = scale,
            present = present,
            batteryLow = batteryLow,
            plugged = plugged,
            propertyCapacity = propertyCapacity,
            propertyChargeCounter = propertyChargeCounter,
            propertyCurrentAverage = propertyCurrentAverage,
            propertyCurrentNow = propertyCurrentNow,
            propertyEnergyCounter = propertyEnergyCounter,
            propertyStatus = propertyStatus,
            isCharging = isCharging,
            chargeTimeRemaining = chargeTimeRemaining
        )
    }


    fun addBatteryData(
        timestamp: String,
        action: String = "",
        health: String = "0",
        status: String = "0",
        voltage: String = "0",
        temperature: String = "0",
        technology: String? = null,
        level: String = "0",
        scale: String = "0",
        present: String = "false",
        batteryLow: String,
        plugged: String = "0",
        propertyCapacity: String = "0",
        propertyChargeCounter: String = "0",
        propertyCurrentAverage: String = "0",
        propertyCurrentNow: String = "0",
        propertyEnergyCounter: String = "0",
        propertyStatus: String? = null,
        isCharging: String? = null,
        chargeTimeRemaining: String? = null
    ) {
        val newBatteryData = getNewBatteryInfoEntry(
            timestamp = timestamp,
            action = action,
            health = health,
            status = status,
            voltage = voltage,
            temperature = temperature,
            technology = technology,
            level = level,
            scale = scale,
            present = present,
            batteryLow = batteryLow,
            plugged = plugged,
            propertyCapacity = propertyCapacity,
            propertyChargeCounter = propertyChargeCounter,
            propertyCurrentAverage = propertyCurrentAverage,
            propertyCurrentNow = propertyCurrentNow,
            propertyEnergyCounter = propertyEnergyCounter,
            propertyStatus = propertyStatus,
            isCharging = isCharging,
            chargeTimeRemaining = chargeTimeRemaining
        )

        insertBatteryInfo(newBatteryData)
    }

    fun getAllBatteryData(): LiveData<List<BatteryData>>{
        return dao.getAllBatteryInfo()
    }



}