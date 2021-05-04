package com.example.itbook.data.source.remote

import com.example.itbook.data.model.Book
import com.example.itbook.data.source.BooksDataSource
import com.example.itbook.utils.LoadDataAsyncTask
import com.example.itbook.utils.OnDataLoadCallBack
import org.json.JSONObject

@Suppress("DEPRECATION")
class BooksRemoteDataSource private constructor(
    private val bookRemoteHandler: BookRemoteHandler
) : BooksDataSource.Remote {

    override fun getRemoteBooks(query: String, callback: OnDataLoadCallBack<List<Book>>) {
        LoadDataAsyncTask(callback) {
            bookRemoteHandler.run { jsonsToBooks(JSONObject(getJsonFromUrl(query))) }
        }.execute()
    }

    companion object {
        private var instance: BooksRemoteDataSource? = null

        fun getInstance(bookRemoteHandler: BookRemoteHandler) =
            instance ?: BooksRemoteDataSource(bookRemoteHandler).also { instance = it }
    }
}
