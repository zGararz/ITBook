package com.example.itbook.data.source.local

import com.example.itbook.data.model.Book
import com.example.itbook.data.source.BooksDataSource
import com.example.itbook.data.source.local.dao.BookDao
import com.example.itbook.utils.LoadDataAsynTask
import com.example.itbook.utils.OnDataLoadCallBack

@Suppress("DEPRECATION")
class BooksLocalDataSource private constructor(
    private val bookDao: BookDao
) : BooksDataSource.Local {

    override fun insertBook(book: Book, callback: OnDataLoadCallBack<Long>) {
        LoadDataAsynTask(callback) {
            bookDao.insertBook(book)
        }.execute()
    }

    override fun getBook(isbn13: String, callback: OnDataLoadCallBack<Book>) {
        LoadDataAsynTask(callback) {
            bookDao.getBook(isbn13)
        }.execute()
    }

    override fun getAllBooks(callback: OnDataLoadCallBack<List<Book>>) {
        LoadDataAsynTask(callback) {
            bookDao.getAllBooks()
        }.execute()
    }

    override fun deleteBook(isbn13: String, callback: OnDataLoadCallBack<Int>) {
        LoadDataAsynTask(callback) {
            bookDao.deleteBook(isbn13)
        }.execute()
    }

    companion object {
        private var instance: BooksLocalDataSource? = null

        fun getInstance(bookDao: BookDao) =
            instance ?: BooksLocalDataSource(bookDao).also { instance = it }
    }
}
