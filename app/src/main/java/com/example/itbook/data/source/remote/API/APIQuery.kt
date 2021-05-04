package com.example.itbook.data.source.remote.API

object APIQuery {
    private const val BASE_URL = "https://api.itbook.store/1.0"
    private const val SEARCH = "search"
    private const val NEW = "new"
    private const val BOOKS = "books"

    fun queryNews(): String = "$BASE_URL/$NEW"

    fun queryBooks(query: String, page: Int): String = "$BASE_URL/$SEARCH/$query/$page"

    fun queryBooks(query: String): String = "$BASE_URL/$SEARCH/$query"

    fun queryBook(isbn13: String): String = "$BASE_URL/$BOOKS/$isbn13"
}
