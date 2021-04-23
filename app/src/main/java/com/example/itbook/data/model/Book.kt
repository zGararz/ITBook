package com.example.itbook.data.model

import android.content.ContentValues
import android.database.Cursor
import org.json.JSONObject

class Book(
    val title: String,
    val subtitle: String,
    val image: String,
    val url: String,
    val authors: String,
    val publisher: String,
    val language: String,
    val isbn10: String,
    val isbn13: String,
    val pages: Int,
    val year: Int,
    val rating: Int,
    val desc: String
) {

    constructor(jsonObject: JSONObject) : this(
        jsonObject.getString(TITLE),
        jsonObject.getString(SUBTITLE),
        jsonObject.getString(IMAGE),
        jsonObject.getString(URL),
        jsonObject.optString(AUTHORS),
        jsonObject.optString(PUBLISHER),
        jsonObject.optString(LANGUAGE),
        jsonObject.optString(ISBN10),
        jsonObject.optString(ISBN13),
        jsonObject.optInt(PAPERS),
        jsonObject.optInt(YEAR),
        jsonObject.optInt(RATING),
        jsonObject.optString(DESC)
    )

    constructor(cursor: Cursor) : this(
        cursor.getString(cursor.getColumnIndex(TITLE)),
        cursor.getString(cursor.getColumnIndex(SUBTITLE)),
        cursor.getString(cursor.getColumnIndex(IMAGE)),
        cursor.getString(cursor.getColumnIndex(URL)),
        cursor.getString(cursor.getColumnIndex(AUTHORS)),
        cursor.getString(cursor.getColumnIndex(PUBLISHER)),
        cursor.getString(cursor.getColumnIndex(LANGUAGE)),
        cursor.getString(cursor.getColumnIndex(ISBN10)),
        cursor.getString(cursor.getColumnIndex(ISBN13)),
        cursor.getInt(cursor.getColumnIndex(PAPERS)),
        cursor.getInt(cursor.getColumnIndex(YEAR)),
        cursor.getInt(cursor.getColumnIndex(RATING)),
        cursor.getString(cursor.getColumnIndex(DESC))
    )

    fun getContentValue() = ContentValues().apply {
        put(ISBN13, isbn13)
        put(TITLE, title)
        put(SUBTITLE, subtitle)
        put(IMAGE, image)
        put(URL, url)
        put(AUTHORS, authors)
        put(PUBLISHER, publisher)
        put(LANGUAGE, language)
        put(ISBN10, isbn10)
        put(PAPERS, pages)
        put(YEAR, year)
        put(RATING, rating)
        put(DESC, desc)
    }

    companion object {
        const val CATEGORY = "category"
        const val BOOKS = "books"
        const val TITLE = "title"
        const val SUBTITLE = "subtitle"
        const val IMAGE = "image"
        const val URL = "url"
        const val AUTHORS = "authors"
        const val PUBLISHER = "publisher"
        const val LANGUAGE = "language"
        const val ISBN10 = "isbn10"
        const val ISBN13 = "isbn13"
        const val PAPERS = "pages"
        const val YEAR = "year"
        const val RATING = "rating"
        const val DESC = "desc"
        const val RATING_0 = 0
        const val RATING_1 = 1
        const val RATING_2 = 2
        const val RATING_3 = 3
        const val RATING_4 = 4
        const val RATING_5 = 5
    }
}
