package com.example.itbook.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.itbook.R
import com.example.itbook.utils.showMessage

@Suppress("DEPRECATION")
class InternetReceiver() : BroadcastReceiver() {
    private var oldConnection = true

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.also {
            val newConnection = isOnline(it)
            if (oldConnection != newConnection) {
                val internetState = if (newConnection) {
                    it.resources.getString(R.string.text_internet_on)
                } else {
                    it.resources.getString(R.string.text_internet_off)
                }

                it.showMessage(internetState)
                oldConnection = newConnection
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isOnline(context: Context) = try {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        (networkInfo != null && networkInfo.isConnected)
    } catch (e: Exception) {
        false
    }
}
