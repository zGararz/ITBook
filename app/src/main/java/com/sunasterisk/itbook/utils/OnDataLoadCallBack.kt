package com.sunasterisk.itbook.utils

interface OnDataLoadCallBack<T> {
    fun onSuccess(data: T)
    fun onFail(message: Exception?)
}
