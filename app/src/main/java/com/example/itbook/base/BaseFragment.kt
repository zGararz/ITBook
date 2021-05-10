package com.example.itbook.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.itbook.R
import com.example.itbook.ui.dialog.LoadingDialogFragment

abstract class BaseFragment : Fragment() {
    protected abstract val layoutResource: Int
    protected var isSetup: Boolean = false
    private val loadingDialogFragment by lazy {
        LoadingDialogFragment().apply { isCancelable = false }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layoutResource, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isSetup) {
            initData()
            isSetup = true
        }
        initViews()
        initListeners()
    }

    protected fun addFragment(
        fragment: Fragment,
        enter: Int,
        exit: Int,
    ) {
        activity?.apply {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(enter, 0, 0, exit)
                .replace(R.id.frameRoot, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    protected fun addFragment(fragment: Fragment) {
        activity?.apply {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameRoot, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun showLoading() {
        activity?.let { loadingDialogFragment.show(it.supportFragmentManager, it.packageName) }
    }

    fun hideLoading() = loadingDialogFragment.dismiss()

    abstract fun initViews()

    abstract fun initData()

    abstract fun initListeners()
}
