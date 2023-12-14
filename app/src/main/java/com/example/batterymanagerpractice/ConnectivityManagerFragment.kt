package com.example.batterymanagerpractice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.batterymanagerpractice.data.MyAndroidViewModel
import com.example.batterymanagerpractice.data.MyAndroidViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Date

class ConnectivityManagerFragment : Fragment() {

    var connectedNetworkInfo: NetworkInfo? = null


    // Declare Views
    lateinit var tv_network_isMetered: TextView
    lateinit var tv_network_type: TextView
    lateinit var tv_network_subtype: TextView
    lateinit var tv_network_state: TextView
    lateinit var tv_network_detailedState: TextView
    lateinit var tv_network_isRoaming: TextView

    lateinit var tv_network_timestamp: TextView

    lateinit var tv_wifi_signalStrength: TextView
    lateinit var tv_wifi_ssid: TextView
    lateinit var tv_wifi_isHiddenSSID: TextView
    lateinit var tv_wifi_bssid: TextView
    lateinit var tv_wifi_linkSpeed: TextView
    lateinit var tv_wifi_frequency: TextView
    lateinit var tv_wifi_macAddress: TextView

    lateinit var tv_telephony_networkType: TextView
    lateinit var tv_telephony_sim: TextView
    lateinit var tv_telephony_imei: TextView
    lateinit var tv_telephony_gsmSignalStrength: TextView

    lateinit var bt_addConnectivityData: FloatingActionButton


    // Initialize ViewModel
    private val viewModel: MyAndroidViewModel by activityViewModels{
        MyAndroidViewModelFactory(
            (activity?.application as MyAndroidApplication).database.dao()
        )
    }


    // Network Info Variables Start ---------------------------------------------------------------------------

    var networkInfo_isMetered: String? = null
    var networkInfo_type: String? = null
    var networkInfo_subType: String? = null
    var networkInfo_state: String? = null
    var networkInfo_detailedState: String? = null
    var networkInfo_isRoaming: String? = null
    var networkInfo_networkID: String? = null


    var networkInfo_wifi_signalStrength: Int? = 0
    var networkInfo_wifi_ssid: String? = null
    var networkInfo_wifi_isHiddenSSID: Boolean? = false
    var networkInfo_wifi_bssid: String? = null
    var networkInfo_wifi_linkSpeed: Double? = 0.0
    var networkInfo_wifi_frequency: Double? = 0.0
    var networkInfo_wifi_macAddress: String? = null

    var networkInfo_telephony_networkType: String? = null
    var networkInfo_telephony_simSerialNumber: String? = null
    var networkInfo_telephony_imei: String? = null
    var networkInfo_telephony_gsmSignalStrength: Double? = 0.0



    // Network Info Variables End ---------------------------------------------------------------------------


    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.d("Network Monitoring Debugging", "onAvailable Called")
        }

        // Network capabilities have changed for the network
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
            Log.d("Network Monitoring Debugging", "onCapabilitiesChanged Called")
        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            Log.d("Network Monitoring Debugging", "onLost Called")
            connectedNetworkInfo = null
        }
    }



    private val connectivityReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager



            connectedNetworkInfo = connectivityManager.activeNetworkInfo


            if (connectedNetworkInfo != null && connectedNetworkInfo!!.isConnected) {
                Log.d(
                    "Network Monitoring Debugging",
                    "Connected to ${connectedNetworkInfo!!.typeName}"
                )

                // Get network capabilities
                val network = connectivityManager.activeNetwork
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                val isMetered = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED) == false
                Log.d("Network Monitoring Debugging", "Is network metered: $isMetered")


                // Get Connectivity Information
                networkInfo_type = connectedNetworkInfo!!.typeName.toString()
                networkInfo_isMetered = isMetered.toString()
                networkInfo_subType = connectedNetworkInfo!!.subtypeName.toString()
                networkInfo_state = connectedNetworkInfo!!.state.toString()
                networkInfo_detailedState = connectedNetworkInfo!!.detailedState.toString()
                networkInfo_isRoaming = connectedNetworkInfo!!.isRoaming.toString()
                networkInfo_networkID = connectedNetworkInfo!!.subtype.toString()

                // Update Network Info
                updateInfo()

                // Update Views
                updateViews()

            } else {
                Log.d("Network Monitoring Debugging", "Not connected to the internet")
                networkInfo_type = "DISCONNECTED"

                // Update Network Info
                updateInfo()

                // Update Views
                updateViews()

            }
        }
    }

    private val telephonyManagerReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val telephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            // Check for permission before accessing network type
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_PHONE_STATE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val phoneStateListener = object : PhoneStateListener() {
                    override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
                        // Access signal strength details
                        networkInfo_telephony_gsmSignalStrength = signalStrength.gsmSignalStrength.toDouble()

                        // Update Network Info
                        updateInfo()

                        // Update Views
                        updateViews()
                    }
                }

                if (intent?.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
                    // Telephony State has changed
                    networkInfo_telephony_networkType = telephonyManager.networkType.toString()
                    // networkInfo_telephony_imei = telephonyManager.imei.toString()
                    // networkInfo_telephony_simSerialNumber = telephonyManager.simSerialNumber

                    // Update Network Info
                    updateInfo()

                    // Update Views
                    updateViews()
                }

                telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS)
            } else {
                // Request the READ_PHONE_STATE permission
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.READ_PHONE_STATE),
                    1
                )
            }
        }
    }



    private val wifiManagerReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val wifiManager = requireContext().getSystemService(Context.WIFI_SERVICE) as WifiManager

            // If WiFi Supplicant State Changes
            if (intent?.action == WifiManager.RSSI_CHANGED_ACTION) {
                val wifiInfo = wifiManager.connectionInfo

                // Get Wi-Fi Data
                networkInfo_wifi_ssid = wifiInfo.ssid.toString()
                networkInfo_wifi_isHiddenSSID = wifiInfo.hiddenSSID
                networkInfo_wifi_bssid = wifiInfo.bssid.toString()
                networkInfo_wifi_frequency = wifiInfo.frequency.toDouble()
                networkInfo_wifi_linkSpeed = wifiInfo.linkSpeed.toDouble()
                networkInfo_wifi_macAddress = wifiInfo.macAddress.toString()
                networkInfo_wifi_signalStrength = wifiInfo.rssi.toInt()

                Log.d("Network Monitoring Debugging", "WIFI Signal Strength Updated: $networkInfo_wifi_signalStrength dBm")

                // Update Network Info
                updateInfo()

                // Update Views
                updateViews()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val connectivityManager = requireContext().getSystemService(ConnectivityManager::class.java)
        if (connectivityManager != null) {
            connectivityManager.requestNetwork(networkRequest, networkCallback)
        }



        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(connectivityReceiver, filter)

        val wifiFilter = IntentFilter(WifiManager.RSSI_CHANGED_ACTION)
        requireContext().registerReceiver(wifiManagerReceiver, wifiFilter)

        val telephonyFilter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        requireContext().registerReceiver(telephonyManagerReceiver, telephonyFilter)

        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_connectivity_manager, container, false)

        // Initialize Views
        tv_network_timestamp = layout.findViewById(R.id.tv_connectivity_timestamp)
        tv_network_isMetered = layout.findViewById(R.id.tv_network_isMetered)
        tv_network_type = layout.findViewById(R.id.tv_network_type)
        tv_network_subtype = layout.findViewById(R.id.tv_network_subtype)
        tv_network_state = layout.findViewById(R.id.tv_network_state)
        tv_network_detailedState = layout.findViewById(R.id.tv_network_detailed_state)
        tv_network_isRoaming = layout.findViewById(R.id.tv_network_isRoaming)

        tv_wifi_signalStrength = layout.findViewById(R.id.tv_wifi_signalStrength)
        tv_wifi_ssid = layout.findViewById(R.id.tv_wifi_ssid)
        tv_wifi_bssid = layout.findViewById(R.id.tv_wifi_BSSID)
        tv_wifi_isHiddenSSID = layout.findViewById(R.id.tv_wifi_isHiddenSSID)
        tv_wifi_linkSpeed = layout.findViewById(R.id.tv_wifi_linkSpeed)
        tv_wifi_frequency = layout.findViewById(R.id.tv_wifi_frequency)
        tv_wifi_macAddress = layout.findViewById(R.id.tv_wifi_MACAddr)

        tv_telephony_networkType = layout.findViewById(R.id.tv_telephony_network_type)
        tv_telephony_sim = layout.findViewById(R.id.tv_telephony_sim)
        tv_telephony_imei = layout.findViewById(R.id.tv_telephony_imei)
        tv_telephony_gsmSignalStrength = layout.findViewById(R.id.tv_telephony_GSMSignalStrength)

        bt_addConnectivityData = layout.findViewById(R.id.bt_addConnectivityInfo)
        bt_addConnectivityData.setOnClickListener {
            addNewConnectivityData()
        }


        return layout
    }

    private fun addNewConnectivityData(){
        viewModel.addConnectivityData(
            timestamp = tv_network_timestamp.text.toString(),
            connectivity_isMetered = tv_network_isMetered.text.toString(),
            connectivity_type = tv_network_type.text.toString(),
            connectivity_subtype = tv_network_subtype.text.toString(),
            connectivity_state =  tv_network_state.text.toString(),
            connectivity_extraInfo = "",
            connectivity_detailedState = tv_network_detailedState.text.toString(),
            connectivity_isRoaming =  tv_network_isRoaming.text.toString(),
            connectivity_networkID = "",
            wifi_signalStrength = networkInfo_wifi_signalStrength?: 0,
            wifi_ssid = tv_wifi_ssid.text.toString(),
            wifi_isHiddenSSID = isHidden,
            wifi_bssid = tv_wifi_bssid.text.toString(),
            wifi_linkSpeed = networkInfo_wifi_linkSpeed?: 0.0,
            wifi_frequency = networkInfo_wifi_frequency?: 0.0,
            wifi_macAddress = tv_wifi_macAddress.text.toString(),
            telephony_networkType = tv_telephony_networkType.text.toString(),
            telephony_sim = tv_telephony_sim.text.toString(),
            telephony_imei = tv_telephony_imei.text.toString(),
            telephony_gsmSignalStrength = tv_telephony_gsmSignalStrength.text.toString()
        )

        findNavController().navigate(R.id.action_connectivityManagerFragment_to_viewConnectivityData)
    }

    override fun onDestroy() {
        super.onDestroy()
        val connectivityManager = requireContext().getSystemService(ConnectivityManager::class.java)
        connectivityManager?.unregisterNetworkCallback(networkCallback)
        requireContext().unregisterReceiver(wifiManagerReceiver)
        requireContext().unregisterReceiver(telephonyManagerReceiver)
        Log.d("Network Monitoring Debugging", "onDestroy Called")
    }

    private fun updateViews(){
        // Network Info Section
        tv_network_timestamp.text = Date().toString()

        tv_network_isMetered.text = MyAndroidViewModel.networkInfo_isMetered
        tv_network_type.text = MyAndroidViewModel.networkInfo_type
        tv_network_subtype.text = MyAndroidViewModel.networkInfo_subType
        tv_network_state.text = MyAndroidViewModel.networkInfo_state
        tv_network_detailedState.text = MyAndroidViewModel.networkInfo_detailedState
        tv_network_isRoaming.text = MyAndroidViewModel.networkInfo_isRoaming

// Wi-Fi Info Section
        tv_wifi_signalStrength.text = "${MyAndroidViewModel.networkInfo_wifi_signalStrength.toString()} dB"
        tv_wifi_bssid.text = MyAndroidViewModel.networkInfo_wifi_bssid
        tv_wifi_ssid.text = MyAndroidViewModel.networkInfo_wifi_ssid
        tv_wifi_isHiddenSSID.text = MyAndroidViewModel.networkInfo_wifi_isHiddenSSID.toString()
        tv_wifi_linkSpeed.text = "${MyAndroidViewModel.networkInfo_wifi_linkSpeed.toString()} Mbps"
        tv_wifi_frequency.text = "${MyAndroidViewModel.networkInfo_wifi_frequency.toString()} Hz"
        tv_wifi_macAddress.text = MyAndroidViewModel.networkInfo_wifi_macAddress.toString()

// Telephony Info Section
        tv_telephony_networkType.text = MyAndroidViewModel.networkInfo_telephony_networkType.toString()
        tv_telephony_sim.text = MyAndroidViewModel.networkInfo_telephony_simSerialNumber.toString()
        tv_telephony_imei.text = MyAndroidViewModel.networkInfo_telephony_imei
        tv_telephony_gsmSignalStrength.text = MyAndroidViewModel.networkInfo_telephony_gsmSignalStrength.toString()
    }

    private fun updateInfo(){

        Log.d("Network Monitoring Debugging", "WIFI Signal Strength Updated: $networkInfo_wifi_signalStrength dBm")
        MyAndroidViewModel.networkInfo_type = networkInfo_type
        MyAndroidViewModel.networkInfo_isMetered = networkInfo_isMetered
        MyAndroidViewModel.networkInfo_subType = networkInfo_subType
        MyAndroidViewModel.networkInfo_state = networkInfo_state
        MyAndroidViewModel.networkInfo_detailedState = networkInfo_detailedState
        MyAndroidViewModel.networkInfo_isRoaming = networkInfo_isRoaming
        MyAndroidViewModel.networkInfo_networkID = networkInfo_networkID

        MyAndroidViewModel.networkInfo_wifi_signalStrength = networkInfo_wifi_signalStrength
        MyAndroidViewModel.networkInfo_wifi_ssid = networkInfo_wifi_ssid
        MyAndroidViewModel.networkInfo_wifi_isHiddenSSID = networkInfo_wifi_isHiddenSSID
        MyAndroidViewModel.networkInfo_wifi_bssid = networkInfo_wifi_bssid
        MyAndroidViewModel.networkInfo_wifi_frequency = networkInfo_wifi_frequency
        MyAndroidViewModel.networkInfo_wifi_linkSpeed = networkInfo_wifi_linkSpeed
        MyAndroidViewModel.networkInfo_wifi_macAddress = networkInfo_wifi_macAddress

        MyAndroidViewModel.networkInfo_telephony_networkType = networkInfo_telephony_networkType
        MyAndroidViewModel.networkInfo_telephony_simSerialNumber = networkInfo_telephony_simSerialNumber
        MyAndroidViewModel.networkInfo_telephony_imei = networkInfo_telephony_imei
        MyAndroidViewModel.networkInfo_telephony_gsmSignalStrength = networkInfo_telephony_gsmSignalStrength

    }

}