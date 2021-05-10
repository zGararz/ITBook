package com.example.itbook.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.itbook.ui.main.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(MainActivity.getIntent(this))
        finish()

    }
}

