package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.AppIntro
import kotlin.system.exitProcess
import android.annotation.SuppressLint
import android.view.Window
import android.view.WindowManager
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.os.Build
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import androidx.core.content.ContextCompat.getSystemService



class WelcomeActivity : AppIntro()  {

    private lateinit var manager: PreferencesManager

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getSupportActionBar()?.hide()

        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        } else {
            val decorView = window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
            decorView.systemUiVisibility = uiOptions
        }

        manager = PreferencesManager(this)
        if (manager.isFirstRun()) {
            addSlide(FirstFragment())
            addSlide(SecondFragment())
            addSlide(ThirdFragment())
            addSlide(FourthFragment())
        } else {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        manager.setFirstRun()
        startActivity(Intent(this,MainActivity::class.java))
    }
    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        manager.setFirstRun()
        startActivity(Intent(this,MainActivity::class.java))
    }
    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) =  super.onSlideChanged(oldFragment, newFragment)

    override fun onBackPressed() {
        moveTaskToBack(true)
        exitProcess(-1)
    }
}
