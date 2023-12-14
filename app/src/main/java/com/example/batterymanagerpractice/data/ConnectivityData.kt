package com.example.batterymanagerpractice.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_connectivity_data")
data class ConnectivityData (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val timestamp: String,
    val connectivity_isMetered: String,
    val connectivity_type: String,
    val connectivity_subtype: String,
    val connectivity_state: String,
    val connectivity_extraInfo: String,
    val connectivity_detailedState: String,
    val connectivity_isRoaming: String,
    val connectivity_networkID: String,
    val wifi_signalStrength: Int,
    val wifi_ssid: String,
    val wifi_isHiddenSSID: Boolean,
    val wifi_bssid: String,
    val wifi_linkSpeed: Double,
    val wifi_frequency: Double,
    val wifi_macAddress: String,
    val telephony_networkType: String,
    val telephony_sim: String,
    val telephony_imei: String,
    val telephony_gsmSignalStrength: String,







    )