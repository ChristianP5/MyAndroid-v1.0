package com.example.batterymanagerpractice.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyAndroidViewModelFactory(private val dao: MainDAO): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MyAndroidViewModel::class.java)){
            @Suppress
            return MyAndroidViewModel(dao) as T
        }
        throw IllegalArgumentException("No ViewModel found")
    }
}