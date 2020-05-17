package com.oselot.android.tlilektik.activities.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oselot.android.tlilektik.R
import com.oselot.android.tlilektik.activities.profileUser.ProfileUserActivity
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        thread {
            Thread.sleep((3 * 1000).toLong())
            startMainActivity()
        }.priority = Thread.NORM_PRIORITY
    }

    private fun startMainActivity() {
        val intent = Intent(applicationContext, ProfileUserActivity::class.java)
        startActivity(intent)
        finish()
    }
}