package com.example.batterymanagerpractice.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [BatteryData::class, ConnectivityData::class], version = 7, exportSchema = false)
abstract class mainDatabase:RoomDatabase() {

    abstract fun dao(): MainDAO

    companion object{
        @Volatile
        private var INSTANCE: mainDatabase? = null

        fun getDatabase(context: Context): mainDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    mainDatabase::class.java,
                    "main_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}