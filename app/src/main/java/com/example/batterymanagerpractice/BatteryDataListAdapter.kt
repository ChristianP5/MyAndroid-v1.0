package com.example.batterymanagerpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.batterymanagerpractice.data.BatteryData

class BatteryDataListAdapter(val batteryDataList: LiveData<List<BatteryData>>): RecyclerView.Adapter<BatteryDataListAdapter.MyViewHolder>() {

    var batteryDataListData : List<BatteryData> = emptyList()
    init{
        batteryDataList.observeForever {
            aBatteryDataList ->
            batteryDataListData = aBatteryDataList
            notifyDataSetChanged()
        }
    }


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tv_timestamp: TextView = itemView.findViewById(R.id.text_timestamp)
        val iv_icon: ImageView = itemView.findViewById(R.id.image_icon_small)
        val tv_health: TextView = itemView.findViewById(R.id.text_health)
        val tv_status: TextView = itemView.findViewById(R.id.text_status)
        val tv_level_scale: TextView = itemView.findViewById(R.id.text_level_scale)
        val tv_plugged: TextView = itemView.findViewById(R.id.text_plugged)
        val tv_present: TextView = itemView.findViewById(R.id.text_present)
        val tv_technology: TextView = itemView.findViewById(R.id.text_technology)
        val tv_batterylow: TextView = itemView.findViewById(R.id.text_battery_low)
        val tv_voltage: TextView = itemView.findViewById(R.id.text_voltage)
        val tv_temperature: TextView = itemView.findViewById(R.id.text_temperature)
        val tv_property_capacity: TextView = itemView.findViewById(R.id.text_property_capacity)
        val tv_property_charge_count: TextView = itemView.findViewById(R.id.text_property_charge_counter)
        val tv_property_energy_counter: TextView = itemView.findViewById(R.id.text_property_energy_counter)
        val tv_property_current_average: TextView = itemView.findViewById(R.id.text_property_current_average)
        val tv_property_current_now: TextView = itemView.findViewById(R.id.text_property_current_now)
        val tv_property_status: TextView = itemView.findViewById(R.id.text_property_status)
        val tv_isCharging: TextView = itemView.findViewById(R.id.text_is_charging)
        val tv_charge_remaining: TextView = itemView.findViewById(R.id.text_charge_time_remaining)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = batteryDataListData[position]

        // Set data to views
        holder.tv_timestamp.text = currentItem.timestamp
        holder.tv_health.text = currentItem.health.toString()
        holder.tv_status.text = currentItem.status.toString()
        holder.tv_level_scale.text = "${currentItem.level} / ${currentItem.scale}"
        holder.tv_plugged.text = currentItem.plugged.toString()
        holder.tv_present.text = currentItem.present.toString()
        holder.tv_technology.text = currentItem.technology ?: "Unknown"
        holder.tv_batterylow.text = currentItem.batteryLow?.toString() ?: "Unknown"
        holder.tv_voltage.text = currentItem.voltage.toString()
        holder.tv_temperature.text = currentItem.temperature.toString()
        holder.tv_property_capacity.text = currentItem.propertyCapacity.toString()
        holder.tv_property_charge_count.text = currentItem.propertyChargeCounter.toString()
        holder.tv_property_energy_counter.text = currentItem.propertyEnergyCounter.toString()
        holder.tv_property_current_average.text = currentItem.propertyCurrentAverage.toString()
        holder.tv_property_current_now.text = currentItem.propertyCurrentNow.toString()
        holder.tv_property_status.text = currentItem.propertyStatus?.toString() ?: "Unknown"
        holder.tv_isCharging.text = currentItem.isCharging?.toString() ?: "-"
        holder.tv_charge_remaining.text = currentItem.chargeTimeRemaining?.toString() ?: "-"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.battery_item, parent, false)
        return MyViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return batteryDataListData.size
    }
}