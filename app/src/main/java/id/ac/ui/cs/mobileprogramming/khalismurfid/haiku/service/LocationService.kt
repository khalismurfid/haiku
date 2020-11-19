package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.service

import android.R.attr.action
import android.app.Service
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.*
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.common.Common
import java.util.*


class LocationService : Service() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        } catch (ignore: SecurityException) {
            Log.e("AppLocationService", "SecurityException - $ignore", ignore)
        }
        return START_STICKY
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList: List<Location> = locationResult.locations
            if (locationList.isNotEmpty()) {
                val location: Location = locationList[0]
                val geocoder = Geocoder(applicationContext, Locale.getDefault())
                val addresses: List<Address> =
                    geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val cityName: String = addresses[0].locality
                val intent = Intent(Common.LOCATION_RECEIVER_KEY)
                intent.putExtra("location", cityName);
                val broadcastManager = LocalBroadcastManager.getInstance(applicationContext)
                broadcastManager.sendBroadcast(intent)
//                Log.e(
//                    "AppLocationService",
//                    "Latitude  - " + location.latitude
//                        .toString() + ", longitude  - " + location.longitude
//                )
            }
        }
    }


    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

}