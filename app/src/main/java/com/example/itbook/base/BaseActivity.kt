package com.example.itbook.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.itbook.R
import com.example.itbook.ui.settings.SettingsFragment
import com.example.itbook.utils.MySharedPreferences
import com.example.itbook.utils.setLanguage

abstract class BaseActivity : AppCompatActivity() {
    protected abstract val layoutResource: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLanguage()
        setContentView(layoutResource)
        initComponents()
    }

    protected abstract fun initComponents()

    private fun getLanguage() {
        MySharedPreferences.getInstance(this)
            .getString(
                SettingsFragment.STRING_LANGUAGE,
                resources.getString(R.string.text_language_en)
            )
            ?.let { setLanguage(it) }
    }
}
