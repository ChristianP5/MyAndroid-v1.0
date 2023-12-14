package com.example.batterymanagerpractice.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MainDAO {
    @Insert
    suspend fun insertBatteryData(batteryData: BatteryData)

    @Update
    suspend fun updateBatteryData(batteryData: BatteryData)

    @Query("SELECT * FROM tb_battery_data WHERE id = :id")
    fun getBatterInfo(id: Int): LiveData<BatteryData>

    @Query("SELECT * FROM tb_battery_data ORDER BY id DESC")
    fun getAllBatteryInfo(): LiveData<List<BatteryData>>

    @Insert
    suspend fun insertConnectivityData(connectivityData: ConnectivityData)

    @Update
    suspend fun updateConnectivityData(connectivityData: ConnectivityData)

    @Query("SELECT * FROM tb_connectivity_data WHERE id = :id")
    fun getConnectivityInfo(id: Int): LiveData<ConnectivityData>

    @Query("SELECT * FROM tb_connectivity_data ORDER BY id DESC")
    fun getAllConnectivityInfo(): LiveData<List<ConnectivityData>>

}