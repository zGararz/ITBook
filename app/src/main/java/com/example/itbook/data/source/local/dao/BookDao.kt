package com.example.itbook.data.source.local.dao

import com.example.itbook.data.model.Book

interface BookDao {
    fun insertBook(book: Book): Long
    fun getBook(isbn13: String): Book?
    fun getAllBooks(): List<Book>?
    fun deleteBook(isbn13: String): Int
}
