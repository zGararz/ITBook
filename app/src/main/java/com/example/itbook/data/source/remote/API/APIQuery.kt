package com.example.itbook.data.source.remote.API

object APIQuery {
    const val BASE_URL = "https://api.itbook.store/1.0"
    const val SEARCH = "search"
    const val NEW = "new"
    const val BOOKS = "books"

    fun queryNews(): String = "$BASE_URL/$NEW"

    fun queryBooks(query: String, page: Int): String = "$BASE_URL/$SEARCH/$query/$page"

    fun queryBook(isbn13: String): String = "$BASE_URL/$NEW"
}
