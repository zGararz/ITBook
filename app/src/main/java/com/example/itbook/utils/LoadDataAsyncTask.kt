package com.example.itbook.utils

import android.os.AsyncTask

@Suppress("DEPRECATION")
class LoadDataAsyncTask<T>(
    private val callBack: OnDataLoadCallBack<T>,
    private val handler: () -> T?
) : AsyncTask<Unit, Unit, T?>() {

    private var error: Exception? = null

    override fun doInBackground(vararg params: Unit?): T? {
        return try {
            handler()
        } catch (e: Exception) {
            error = e
            null
        }
    }

    override fun onPostExecute(result: T?) {
        super.onPostExecute(result)
        result?.let { callBack.onSuccess(it) } ?: callBack.onFail(error)
    }
}
