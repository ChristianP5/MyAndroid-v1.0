package com.example.batterymanagerpractice.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tb_battery_data")
data class BatteryData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val timestamp: String,
    val action: String,
    val health: String,
    val status: String,
    val voltage: Int,
    val temperature: Int,
    val technology: String?,
    val level: String,
    val scale: String,
    val present: String,
    val batteryLow: String,
    val plugged: String,
    val propertyCapacity: String,
    val propertyChargeCounter: String,
    val propertyCurrentAverage: String,
    val propertyCurrentNow: String,
    val propertyEnergyCounter: String,

    // Min. API Level 26
    val propertyStatus: String?,

    // Min. API Level 23
    val isCharging: String?,

    // Min. API Level 29
    val chargeTimeRemaining: String?,
)
