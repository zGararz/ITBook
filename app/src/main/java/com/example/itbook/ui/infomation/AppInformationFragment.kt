package com.example.itbook.ui.infomation

import androidx.activity.OnBackPressedCallback
import com.example.itbook.BuildConfig
import com.example.itbook.R
import com.example.itbook.base.BaseFragment
import com.example.itbook.ui.settings.SettingsFragment
import com.example.itbook.utils.LinkBuilder
import kotlinx.android.synthetic.main.fragment_app_info.*

class AppInformationFragment : BaseFragment() {
    override val layoutResource: Int = R.layout.fragment_app_info

    private val backPressedCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            addFragment(SettingsFragment())
        }
    }

    override fun initViews() {
        textVersion.text = BuildConfig.VERSION_NAME
        LinkBuilder().build(textContact, resources.getString(R.string.text_facebook))
    }

    override fun initData() {}

    override fun initListeners() {
        imageBackAppInfo.setOnClickListener { onBackClickListener() }
        activity?.onBackPressedDispatcher?.addCallback(backPressedCallBack)
    }

    private fun onBackClickListener() = addFragment(SettingsFragment())
}
