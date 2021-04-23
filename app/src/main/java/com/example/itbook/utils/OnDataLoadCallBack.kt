package com.example.itbook.utils

interface OnDataLoadCallBack<T> {
    fun onSuccess(data: T)
    fun onFail(message: Exception?)
}
