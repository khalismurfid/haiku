package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.*
import android.widget.RemoteViews
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.R
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.application.PoemApplication
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.common.Common
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.databinding.ActivityMainBinding
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui.viewmodel.PoemViewModel
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui.viewmodel.PoemViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {
    val poemViewModel: PoemViewModel by viewModels {
        PoemViewModelFactory((application as PoemApplication).repository)
    }
    var location: String? = null
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel : NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val extras = intent.extras
        if (extras != null) {
            location = extras.getString("location")
            //The key argument here must match that used in the other activity
        }
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        binding.bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = HomeFragment()
        addFragment(fragment)

        mHandler = Handler(Looper.getMainLooper())
        mRunnable = Runnable {
            Common.timePassed += 1
            if(Common.timePassed % 60 == 0){
                sendNotification()
            }
            mHandler.postDelayed(mRunnable, 1000)
        }
        mHandler.postDelayed(mRunnable, 1000)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "i.apps.notifications"
            val descriptionText = "Haiku Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val textTitle = "Friendly Reminder"
        val textContent = "You have used this app for too long!! Go get a life please!!"

        // Create an explicit intent for an Activity in your app
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText(textContent)
            .setContentTitle(textTitle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.page_home -> {
                val fragment = HomeFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.page_collections -> {
                val fragment = CollectionsFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.content_main, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}