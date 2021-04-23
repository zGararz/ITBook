package com.example.itbook.ui.search

import com.example.itbook.data.model.Book
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.data.source.remote.API.APIQuery
import com.example.itbook.utils.OnDataLoadCallBack

@Suppress("ControlFlowWithEmptyBody")
class SearchPresenter(
    private val view: SearchContract.View,
    private val repository: BooksRepository
) : SearchContract.Presenter {

    private var isStop = false

    override fun getSearchBook(query: String) {
        val books = mutableListOf<Book>()
        var currentPage = 1

        view.showLoading()
        repository.getRemoteBooks(query, currentPage, object : OnDataLoadCallBack<List<Book>> {
                override fun onSuccess(data: List<Book>) {
                    books.addAll(data)
                    view.showSearchResult(books)
                    view.hideLoading()

                    if (data.isNotEmpty() && !isStop) {
                        currentPage++
                        repository.getRemoteBooks(APIQuery.queryBooks(query, currentPage), this)
                    } else {
                        if (data.isNotEmpty()) view.resetResult()
                        isStop = false
                    }
                }

                override fun onFail(message: Exception?) {
                    view.showError(message)
                    view.hideLoading()
                }
            })
    }

    override fun stop() {
        isStop = true
    }

    override fun start() {
        isStop = false
    }
}
