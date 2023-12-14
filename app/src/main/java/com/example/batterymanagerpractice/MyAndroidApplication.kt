package com.example.batterymanagerpractice

import android.app.Application
import com.example.batterymanagerpractice.data.mainDatabase

class MyAndroidApplication:Application() {
    val database: mainDatabase by lazy { mainDatabase.getDatabase(this) }
}