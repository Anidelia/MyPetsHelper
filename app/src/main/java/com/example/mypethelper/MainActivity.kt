package com.example.mypethelper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.mypethelper.databinding.ActivityMainBinding
import io.ak1.BubbleTabBar
import io.ak1.OnBubbleClickListener
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private var is_frag = 1
    private val liveData: DataForElement by viewModels()
    private lateinit var bubble: BubbleTabBar
    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>
    private var accelerationValues = FloatArray(3)
    private var lastAccelerationValues = FloatArray(3)
    private var shakeThreshold = 30.5f
    private var minimum_needed_distance = 0.010
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //https://github.com/akshay2211/BubbleTabBar
        bubble = binding.bubbleTabBar
        //liveData.flag_anim.value = false
        replaceFragment(ArticleList())
        setStatusBarColor("#8F847E")
        bubble.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                when (id) {
                    R.id.List -> {
                        is_frag = 1
                        replaceFragment(ArticleList())
                    }

                    R.id.Pet -> {
                        frg = Map()
                        replaceFragment(Map())
                        is_frag = 2
                    }

                    R.id.Profile -> {
                        is_frag = 3
                        replaceFragment(Profile())
                    }
                }
            }
        })



    fun replaceFragment(fragment: Fragment) {
        if (is_frag != 2) {
            Log.i("Dibug1", "fragment")
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.BAZA, fragment)
            fragmentTransaction.commit()
        }
    }



    fun setStatusBarColor(color: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            val statusBarColor: Int = Color.parseColor(color)
            if (statusBarColor == Color.BLACK && window.getNavigationBarColor() === Color.BLACK) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            }
            window.setStatusBarColor(statusBarColor)
        }
    }
        return
    }


}