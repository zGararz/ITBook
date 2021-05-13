@file:Suppress("DEPRECATION")

package com.example.itbook.utils

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import com.example.itbook.ui.main.MainActivity
import java.util.*

fun Activity.setLanguage(locale: String) {
    val locale = Locale(locale)
    val configuration = Configuration()
    configuration.locale = locale
    baseContext.resources.updateConfiguration(configuration, baseContext.resources.displayMetrics)
}

fun Activity.restart() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    finish()
}
