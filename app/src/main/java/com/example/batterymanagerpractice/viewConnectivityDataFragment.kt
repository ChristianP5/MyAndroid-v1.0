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


class viewConnectivityDataFragment : Fragment() {

    private lateinit var rv_connectivityDataList: RecyclerView

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
        val layout = inflater.inflate(R.layout.fragment_view_connectivity_data, container, false)

        // Initialize Views
        rv_connectivityDataList = layout.findViewById(R.id.rv_connectivityData)
        rv_connectivityDataList.layoutManager = LinearLayoutManager(requireContext())
        rv_connectivityDataList.setHasFixedSize(true)

        rv_connectivityDataList.adapter = ConnectivityDataListAdapter(viewModel.getAllConnectivityData())

        return layout
    }


}