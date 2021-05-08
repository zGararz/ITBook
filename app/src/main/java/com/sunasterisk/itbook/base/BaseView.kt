package com.sunasterisk.itbook.base

interface BaseView {
    fun showError(error: Exception?)
    fun showLoading()
    fun hideLoading()
}
