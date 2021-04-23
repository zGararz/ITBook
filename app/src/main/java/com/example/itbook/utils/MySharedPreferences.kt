package com.example.itbook.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

class MySharedPreferences private constructor(context: Context) {
    private val preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
    private val editor = preferences.edit()

    fun writeString(key: String, value: String) {
        editor.apply {
            putString(key, value)
            commit()
        }
    }

    fun getString(key: String, defaultString: String) = preferences.getString(key, defaultString)

    companion object {
        const val SHARED_PREFERENCES_NAME = "it"
        private var instance: MySharedPreferences? = null

        fun getInstance(context: Context) =
            instance ?: MySharedPreferences(context).also { instance = it }
    }
}
