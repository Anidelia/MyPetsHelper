package com.example.mypethelper

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.mypethelper.DataClasses.Article
import com.example.mypethelper.databinding.ActivityMainBinding
import io.ak1.BubbleTabBar
import io.ak1.OnBubbleClickListener

class MainActivity : AppCompatActivity() {
    private var is_frag = 1
    private val liveData: DataForElement by viewModels()
    private lateinit var bubble: BubbleTabBar
    private lateinit var binding: ActivityMainBinding

    fun replaceFragment(fragment: Fragment) {
        Log.i("Dibug1", "fragment")
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.BAZA, fragment)
        fragmentTransaction.commit()
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //https://github.com/akshay2211/BubbleTabBar
        bubble = binding.bubbleTabBar
        replaceFragment(ArticleList())
        setStatusBarColor("#8F847E")
        bubble.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                when (id) {
                    R.id.Home -> {
                        is_frag = 1
                        replaceFragment(ArticleList())
                    }

                    R.id.Pet -> {
                        is_frag = 2
                        replaceFragment(Pet())
                    }

                    R.id.Profile -> {
                        is_frag = 3
                        replaceFragment(Profile())
                    }
                }
            }
        })
        return
    }
    fun ListenerForArticle(p : Article){
        liveData.data.value = p
        liveData.flag_view.value = true
        val bottomSheet = ArticleFull()
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        is_frag = 1
    }
}