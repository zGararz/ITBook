package com.example.itbook.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.itbook.R

fun ImageView.loadImageFromUri(uri: String) {
    Glide.with(context)
        .load(uri)
        .placeholder(R.drawable.book)
        .into(this)
}
