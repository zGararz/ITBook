package com.example.itbook.utils

import android.content.Context
import android.widget.Toast

fun Context.showError(error: String) {
    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
}
