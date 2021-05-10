package com.example.itbook.utils

import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView

@Suppress("DEPRECATION")
class LinkBuilder {
    fun build(textView: TextView, link: String) {
        val str_links = "<a href='$link'>$link</a>"
        textView.linksClickable = true
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.text = Html.fromHtml(str_links)
    }
}
