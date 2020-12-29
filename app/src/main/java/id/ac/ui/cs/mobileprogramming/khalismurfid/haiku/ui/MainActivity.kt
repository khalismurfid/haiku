package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            mHandler.postDelayed(mRunnable, 1000)
        }
        mHandler.postDelayed(mRunnable, 1000)
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