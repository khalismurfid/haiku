package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui

import MyGLSurfaceView
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
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



}