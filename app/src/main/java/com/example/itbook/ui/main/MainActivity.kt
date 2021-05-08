package com.example.itbook.ui.main

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.Fragment
import com.example.itbook.R
import com.example.itbook.base.BaseActivity
import com.example.itbook.broadcast.InternetReceiver
import com.example.itbook.ui.favorite.FavoriteBooksFragment
import com.example.itbook.ui.home.HomeFragment
import com.example.itbook.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val layoutResource = R.layout.activity_main

    private val favoriteFragment = FavoriteBooksFragment()
    private val homeFragment = HomeFragment()
    private var receiver: InternetReceiver? = null

    override fun initComponents() {
        registerInternetReceiver()
        bottomNav.setOnNavigationItemSelectedListener(onBottomNavigationListener)
        bottomNav.selectedItemId = R.id.menuHome
    }

    private val onBottomNavigationListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuFavoriteBooks -> replaceFragment(favoriteFragment)
                R.id.menuHome -> replaceFragment(homeFragment)
                R.id.menuSettings -> replaceFragment(SettingsFragment())
            }
            true
        }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameRoot, fragment)
            .commit()
    }

    private fun registerInternetReceiver() {
        val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        receiver = InternetReceiver()
        registerReceiver(receiver, filter)
    }

    override fun onStop() {
        super.onStop()
        receiver?.let { unregisterReceiver(it) }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
