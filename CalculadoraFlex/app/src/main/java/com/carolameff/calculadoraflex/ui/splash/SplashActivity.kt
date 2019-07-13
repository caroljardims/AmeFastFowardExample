package com.carolameff.calculadoraflex.ui.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.carolameff.calculadoraflex.R
import com.carolameff.calculadoraflex.ui.form.FormActivity
import com.carolameff.calculadoraflex.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_AWAIT = 3500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val preferences = getSharedPreferences("user_preferences",
            Context.MODE_PRIVATE)
        val isFirstOpen = preferences.getBoolean("open_first", true)
        if (isFirstOpen) {
            showLogin()
            return
        }
        markAppAlreadyOpen(preferences)
        load()
    }

    private fun markAppAlreadyOpen(preferences: SharedPreferences?) {
        val editor = preferences?.edit()
        editor?.putBoolean("open_first", false)
        editor?.apply()
    }

    private fun showLogin() {
        val login = Intent(this, LoginActivity::class.java)
        startActivity(login)
        finish()
    }

    private fun load() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        anim.reset()
        gasStationLogo.clearAnimation()
        gasStationLogo.startAnimation(anim)

        Handler().postDelayed({
            val nextScreen = Intent(this@SplashActivity,FormActivity::class.java)
            startActivity(nextScreen)
            finish()
        }, SPLASH_TIME_AWAIT)
    }
}
