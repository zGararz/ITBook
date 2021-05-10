package com.example.itbook.data.source.local.dao

import com.example.itbook.data.model.Book

interface BookDao {
    fun insertBook(book: Book): Long
    fun getBook(id: String): Book?
    fun getAllBooks(): List<Book>?
    fun deleteBook(id: String): Int
}
