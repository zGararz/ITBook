package com.example.itbook.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.itbook.R

abstract class BaseFragment : Fragment() {
    protected abstract val layoutResource: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layoutResource, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
        initListeners()
    }

    protected fun addFragment(
        fragment: Fragment,
        enter: Int,
        exit: Int,
        popEnter: Int,
        popExit: Int
    ) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.add(R.id.frameRoot, fragment)
            ?.setCustomAnimations(enter, exit, popEnter, popExit)
            ?.commit()
    }

    protected fun addFragment(fragment: Fragment) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.frameRoot, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    abstract fun initViews()

    abstract fun initData()

    abstract fun initListeners()
}
