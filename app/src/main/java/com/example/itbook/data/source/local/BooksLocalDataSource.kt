package com.example.itbook.data.source.local

import com.example.itbook.data.model.Book
import com.example.itbook.data.source.BooksDataSource
import com.example.itbook.data.source.local.dao.BookDao
import com.example.itbook.utils.LoadDataAsyncTask
import com.example.itbook.utils.OnDataLoadCallBack

@Suppress("DEPRECATION")
class BooksLocalDataSource private constructor(
    private val bookDao: BookDao
) : BooksDataSource.Local {

    override fun insertBook(book: Book, callback: OnDataLoadCallBack<Long>) {
        LoadDataAsyncTask(callback) {
            bookDao.insertBook(book)
        }.execute()
    }

    override fun getBook(id: String, callback: OnDataLoadCallBack<Book>) {
        LoadDataAsyncTask(callback) {
            bookDao.getBook(id)
        }.execute()
    }

    override fun getAllBooks(callback: OnDataLoadCallBack<List<Book>>) {
        LoadDataAsyncTask(callback) {
            bookDao.getAllBooks()
        }.execute()
    }

    override fun deleteBook(id: String, callback: OnDataLoadCallBack<Int>) {
        LoadDataAsyncTask(callback) {
            bookDao.deleteBook(id)
        }.execute()
    }

    companion object {
        private var instance: BooksLocalDataSource? = null

        fun getInstance(bookDao: BookDao) =
            instance ?: BooksLocalDataSource(bookDao).also { instance = it }
    }
}
