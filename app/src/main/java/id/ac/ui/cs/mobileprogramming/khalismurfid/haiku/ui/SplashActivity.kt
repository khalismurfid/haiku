package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.R
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.service.LocationService
import java.util.*

class SplashActivity : AppCompatActivity() {
    companion object{
        val LOCATION_REQUEST_CODE = 1001
    }
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkForPermission()
//        handler = Handler()
//        handler.postDelayed({
//            val intent = Intent(this@SplashActivity, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 0)
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
            Intent(this, LocationService::class.java).also { intent ->
                startService(intent)
            }
        }
    }



}