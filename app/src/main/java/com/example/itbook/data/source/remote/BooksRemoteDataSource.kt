package com.example.itbook.data.source.remote

import com.example.itbook.data.model.Book
import com.example.itbook.data.source.BooksDataSource
import com.example.itbook.data.source.remote.API.APIQuery
import com.example.itbook.utils.LoadDataAsyncTask
import com.example.itbook.utils.OnDataLoadCallBack
import org.json.JSONObject

@Suppress("DEPRECATION")
class BooksRemoteDataSource private constructor(
    private val bookRemoteHandler: BookRemoteHandler
) : BooksDataSource.Remote {
    override fun getNewsBook(callback: OnDataLoadCallBack<List<Book>>) {
        LoadDataAsyncTask(callback) {
            bookRemoteHandler.run { jsonsToBooks(JSONObject(getJsonFromUrl(APIQuery.queryNews()))) }
        }.execute()
    }

    override fun getRemoteBook(id: String, callback: OnDataLoadCallBack<Book>) {
        LoadDataAsyncTask(callback) {
            bookRemoteHandler.run { jsonsToBook(JSONObject(getJsonFromUrl(APIQuery.queryBook(id)))) }
        }.execute()
    }

    override fun getRemoteBooks(query: String, callback: OnDataLoadCallBack<List<Book>>) {
        LoadDataAsyncTask(callback) {
            bookRemoteHandler.run { jsonsToBooks(JSONObject(getJsonFromUrl(APIQuery.queryBooks(query)))) }
        }.execute()
    }

    override fun getRemoteBooks(
        query: String,
        page: Int,
        callback: OnDataLoadCallBack<List<Book>>
    ) {
        LoadDataAsyncTask(callback) {
            bookRemoteHandler.run {
                jsonsToBooks(JSONObject(getJsonFromUrl(APIQuery.queryBooks(query, page))))
            }
        }.execute()
    }

    companion object {
        private var instance: BooksRemoteDataSource? = null

        fun getInstance(bookRemoteHandler: BookRemoteHandler) =
            instance ?: BooksRemoteDataSource(bookRemoteHandler).also { instance = it }
    }
}
