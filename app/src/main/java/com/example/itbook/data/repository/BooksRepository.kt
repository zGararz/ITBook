package com.example.itbook.data.repository

import com.example.itbook.data.model.Book
import com.example.itbook.data.source.BooksDataSource
import com.example.itbook.utils.OnDataLoadCallBack

class BooksRepository private constructor(
    private val remote: BooksDataSource.Remote,
    private val local: BooksDataSource.Local
) : BooksDataSource.Remote, BooksDataSource.Local {
    override fun getNewsBook(callback: OnDataLoadCallBack<List<Book>>) {
        remote.getNewsBook(callback)
    }

    override fun getRemoteBook(id: String, callback: OnDataLoadCallBack<Book>) {
        remote.getRemoteBook(id, callback)
    }

    override fun getRemoteBooks(query: String, callback: OnDataLoadCallBack<List<Book>>) {
        remote.getRemoteBooks(query, callback)
    }

    override fun getRemoteBooks(
        query: String,
        page: Int,
        callback: OnDataLoadCallBack<List<Book>>
    ) {
        remote.getRemoteBooks(query, page, callback)
    }

    override fun insertBook(book: Book, callback: OnDataLoadCallBack<Long>) {
        local.insertBook(book, callback)
    }

    override fun getBook(id: String, callback: OnDataLoadCallBack<Book>) {
        local.getBook(id, callback)
    }

    override fun getAllBooks(callback: OnDataLoadCallBack<List<Book>>) {
        local.getAllBooks(callback)
    }

    override fun deleteBook(id: String, callback: OnDataLoadCallBack<Int>) {
        local.deleteBook(id, callback)
    }

    companion object {
        private var instance: BooksRepository? = null
        fun getInstance(remote: BooksDataSource.Remote, local: BooksDataSource.Local) =
            instance ?: BooksRepository(remote, local).also { instance = it }
    }
}
