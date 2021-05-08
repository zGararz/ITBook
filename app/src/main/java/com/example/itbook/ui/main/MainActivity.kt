package com.example.itbook.ui.main

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.itbook.R
import com.example.itbook.base.BaseActivity
import com.example.itbook.ui.favorite.FavoriteBooksFragment
import com.example.itbook.ui.home.HomeFragment
import com.example.itbook.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : BaseActivity() {
    override val layoutResource = R.layout.activity_main

    private val favoriteFragment = FavoriteBooksFragment()
    private val homeFragment = HomeFragment()
    override fun initComponents() {
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

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
