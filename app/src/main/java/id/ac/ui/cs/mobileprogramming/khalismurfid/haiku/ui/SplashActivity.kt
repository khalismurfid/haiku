package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui

import MyGLSurfaceView
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.DialogInterface
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.net.NetworkInfo
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.net.ConnectivityManager
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.R
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.common.Common
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.service.LocationService

class SplashActivity : AppCompatActivity() {
    companion object{
        const val LOCATION_REQUEST_CODE = 1001
    }
    private lateinit var handler: Handler
    private var locationReceiver: BroadcastReceiver? = null
    private lateinit var gLView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gLView = MyGLSurfaceView(this)
        setContentView(gLView)
//        setContentView(R.layout.activity_splash)

        if(isNetworkAvailable(this)){
            checkForPermission()

            locationReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (intent.action.toString() == Common.LOCATION_RECEIVER_KEY) {
                        val location = intent.getStringExtra("location")
                        stopLocationService()
                        val intent_main = Intent(this@SplashActivity, MainActivity::class.java)
                        intent_main.putExtra("location", location);
                        navigateToMainActivity(intent_main)
                    }
                }
            }
            // Register broadcast
            LocalBroadcastManager.getInstance(this).registerReceiver(locationReceiver as BroadcastReceiver, IntentFilter(Common.LOCATION_RECEIVER_KEY))
        } else {
            showAlertMenu()
        }
    }

    override fun onStop() {
        locationReceiver?.let { LocalBroadcastManager.getInstance(this).unregisterReceiver(it) }
        super.onStop()
    }

    fun navigateToMainActivity(intent: Intent){
        startActivity(intent)
        this.finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // pick image after request permission success
                    Intent(this, LocationService::class.java).also { intent ->
                        startService(intent)
                    }
                } else {
                    showDenyPermissionDialog()
                }
            }
        }
    }

    private fun checkForPermission(){
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                , LOCATION_REQUEST_CODE)
        } else {
            startLocationService()
        }
    }

    private fun startLocationService(){
        Intent(this, LocationService::class.java).also { intent ->
            startService(intent)
        }
    }
    private fun stopLocationService(){
        Intent(this, LocationService::class.java).also { intent ->
            stopService(intent)
        }
    }
    private fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
    private fun showAlertMenu() {
        val builder =
            AlertDialog.Builder(this)

        builder.setMessage("Haiku Need Internet Connection for Better Location Tagging")
        builder.setTitle("Warning!")
        builder.setCancelable(false)

        builder
            .setPositiveButton(
                "Close App",
                DialogInterface.OnClickListener { dialog, which ->
                    // then app will close
                    finish()
                })

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun showDenyPermissionDialog() {
        val builder =
            AlertDialog.Builder(this)

        builder.setMessage("Haiku Need Location Access Permission to tag poem with current location. Please grant the permission manually from setting to continue using Haiku.")
        builder.setTitle("Warning!")
        builder.setCancelable(false)

        builder
            .setPositiveButton(
                "Close App",
                DialogInterface.OnClickListener { dialog, which ->
                    // then app will close
                    finish()
                })

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}