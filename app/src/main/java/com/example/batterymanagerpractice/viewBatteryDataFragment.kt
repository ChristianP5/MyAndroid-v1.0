package com.example.batterymanagerpractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.batterymanagerpractice.data.MyAndroidViewModel
import com.example.batterymanagerpractice.data.MyAndroidViewModelFactory


class viewBatteryDataFragment : Fragment() {

    lateinit var rv_batteryDataList : RecyclerView

    private val viewModel: MyAndroidViewModel by activityViewModels{
        MyAndroidViewModelFactory(
            (activity?.application as MyAndroidApplication).database.dao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_view_battery_data, container, false)

        rv_batteryDataList = layout.findViewById(R.id.rv_batteryData)
        rv_batteryDataList.layoutManager = LinearLayoutManager(requireContext())
        rv_batteryDataList.setHasFixedSize(true)

        rv_batteryDataList.adapter = BatteryDataListAdapter(viewModel.getAllBatteryData())


        return layout
    }


}