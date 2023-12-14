package com.example.batterymanagerpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.batterymanagerpractice.data.ConnectivityData

class ConnectivityDataListAdapter(val connectivityDataList: LiveData<List<ConnectivityData>>): RecyclerView.Adapter<ConnectivityDataListAdapter.MyViewHolder>() {

    var connectivityDataListData: List<ConnectivityData> = emptyList()
    init {
        connectivityDataList.observeForever { aConnectivityDataList ->
            connectivityDataListData = aConnectivityDataList
            notifyDataSetChanged()
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        // Declare Views
        val tv_network_timestamp: TextView = itemView.findViewById(R.id.tv_view_connectivity_timestamp)
        val tv_network_isMetered: TextView = itemView.findViewById(R.id.tv_view_network_isMetered)
        val tv_network_type: TextView = itemView.findViewById(R.id.tv_view_network_type)
        val tv_network_subtype: TextView = itemView.findViewById(R.id.tv_view_network_subtype)
        val tv_network_state: TextView = itemView.findViewById(R.id.tv_view_network_state)
        val tv_network_detailedState: TextView = itemView.findViewById(R.id.tv_view_network_detailed_state)
        val tv_network_isRoaming: TextView = itemView.findViewById(R.id.tv_view_network_isRoaming)

        val tv_wifi_signalStrength: TextView = itemView.findViewById(R.id.tv_view_wifi_signalStrength)
        val tv_wifi_ssid: TextView = itemView.findViewById(R.id.tv_view_wifi_ssid)
        val tv_wifi_isHiddenSSID: TextView = itemView.findViewById(R.id.tv_view_wifi_isHiddenSSID)
        val tv_wifi_bssid: TextView = itemView.findViewById(R.id.tv_view_wifi_BSSID)
        val tv_wifi_linkSpeed: TextView = itemView.findViewById(R.id.tv_view_wifi_linkSpeed)
        val tv_wifi_frequency: TextView = itemView.findViewById(R.id.tv_view_wifi_frequency)
        val tv_wifi_macAddress: TextView = itemView.findViewById(R.id.tv_view_wifi_MACAddr)

        val tv_telephony_networkType: TextView = itemView.findViewById(R.id.tv_view_telephony_network_type)
        val tv_telephony_sim: TextView = itemView.findViewById(R.id.tv_view_telephony_sim)
        val tv_telephony_imei: TextView = itemView.findViewById(R.id.tv_view_telephony_imei)
        val tv_telephony_gsmSignalStrength: TextView = itemView.findViewById(R.id.tv_view_telephony_GSMSignalStrength)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.connectivity_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = connectivityDataListData[position]

        holder.tv_network_timestamp.text = currentItem.timestamp
        holder.tv_network_isMetered.text = currentItem.connectivity_isMetered
        holder.tv_network_type.text = currentItem.connectivity_type
        holder.tv_network_subtype.text = currentItem.connectivity_subtype
        holder.tv_network_state.text = currentItem.connectivity_state
        holder.tv_network_detailedState.text = currentItem.connectivity_detailedState
        holder.tv_network_isRoaming.text = currentItem.connectivity_isRoaming

        holder.tv_wifi_signalStrength.text = "${currentItem.wifi_signalStrength.toString()} dB"
        holder.tv_wifi_ssid.text = currentItem.wifi_ssid
        holder.tv_wifi_isHiddenSSID.text = currentItem.wifi_isHiddenSSID.toString()
        holder.tv_wifi_bssid.text = currentItem.wifi_bssid
        holder.tv_wifi_linkSpeed.text = "${currentItem.wifi_linkSpeed} Mbps"
        holder.tv_wifi_frequency.text = "${currentItem.wifi_frequency} Hz"
        holder.tv_wifi_macAddress.text = currentItem.wifi_macAddress

        holder.tv_telephony_networkType.text = currentItem.telephony_networkType
        holder.tv_telephony_sim.text = currentItem.telephony_sim
        holder.tv_telephony_imei.text = currentItem.telephony_imei
        holder.tv_telephony_gsmSignalStrength.text =  currentItem.telephony_gsmSignalStrength




    }

    override fun getItemCount(): Int {
        return connectivityDataListData.size
    }


}