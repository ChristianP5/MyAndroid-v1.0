package com.example.batterymanagerpractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController


class ViewFragment : Fragment() {

    private lateinit var bt_toBatteryDataList: Button
    private lateinit var bt_toConnectivityDataList: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_view, container, false)

        // Initialize Views
        bt_toBatteryDataList = layout.findViewById(R.id.bt_toBatteryDataList)
        bt_toConnectivityDataList = layout.findViewById(R.id.bt_toConectivityDataList)

        // Set Listeners
        bt_toConnectivityDataList.setOnClickListener {
            findNavController().navigate(R.id.action_viewFragment_to_viewConnectivityData)
        }

        bt_toBatteryDataList.setOnClickListener {
            findNavController().navigate(R.id.action_viewFragment_to_viewBatteryDataFragment)
        }

        return layout
    }


}