package com.example.itbook.ui.settings

import android.widget.CompoundButton
import com.example.itbook.R
import com.example.itbook.base.BaseFragment
import com.example.itbook.ui.infomation.AppInformationFragment
import com.example.itbook.utils.MySharedPreferences
import com.example.itbook.utils.restart
import com.example.itbook.utils.setLanguage
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment() {
    override val layoutResource = R.layout.fragment_settings

    private var preferences: MySharedPreferences? = null
    private var enableSwitchChange = true

    override fun initViews() {
    }

    override fun initData() {
        preferences = context?.let { MySharedPreferences.getInstance(it) }
        preferences?.getString(STRING_LANGUAGE, resources.getString(R.string.text_language_en))
            ?.let { setSwitch(it) }
    }

    override fun initListeners() {
        switchLanguage.setOnCheckedChangeListener(onCheckedChangeListener)
        textAppInfo.setOnClickListener { addFragment(AppInformationFragment()) }
        imageInfo.setOnClickListener { addFragment(AppInformationFragment()) }
    }

    private fun setSwitch(language: String) {
        enableSwitchChange = false
        switchLanguage.isChecked = language != resources.getString(R.string.text_language_en)
        enableSwitchChange = true
    }

    private val onCheckedChangeListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        var language = resources.getString(R.string.text_language_en)

        if (enableSwitchChange) {
            activity?.apply {
                if (isChecked) language = resources.getString(R.string.text_language_vi)
                setLanguage(language)
                context?.let { preferences?.writeString(STRING_LANGUAGE, language) }
                restart()
            }
        }
    }

    companion object {
        const val STRING_LANGUAGE = "language"
    }
}
