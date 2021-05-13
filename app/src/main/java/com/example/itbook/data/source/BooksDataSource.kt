package com.example.itbook.data.source

import com.example.itbook.data.model.Book
import com.example.itbook.utils.OnDataLoadCallBack

interface BooksDataSource {
    interface Remote {
        fun getNewsBook(callback: OnDataLoadCallBack<List<Book>>)
        fun getRemoteBook(id: String, callback: OnDataLoadCallBack<Book>)
        fun getRemoteBooks(query: String, callback: OnDataLoadCallBack<List<Book>>)
        fun getRemoteBooks(query: String, page: Int, callback: OnDataLoadCallBack<List<Book>>)
    }

    interface Local {
        fun insertBook(book: Book, callback: OnDataLoadCallBack<Long>)
        fun getBook(id: String, callback: OnDataLoadCallBack<Book>)
        fun getAllBooks(callback: OnDataLoadCallBack<List<Book>>)
        fun deleteBook(id: String, callback: OnDataLoadCallBack<Int>)
    }
}
