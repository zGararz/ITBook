package com.sunasterisk.itbook.ui.search

import com.sunasterisk.itbook.data.model.Book
import com.sunasterisk.itbook.data.repository.BooksRepository
import com.sunasterisk.itbook.data.source.remote.API.APIQuery
import com.sunasterisk.itbook.utils.OnDataLoadCallBack

@Suppress("ControlFlowWithEmptyBody")
class SearchPresenter(
    private val view: SearchContract.View,
    private val repository: BooksRepository
) : SearchContract.Presenter {

    private var isStop = false
    private var enableSearchBooks = false

    override fun getSearchBook(query: String) {
        if (query.matches(REGEX_ID_BOOK.toRegex())) {
            enableSearchBooks = false
            getBook(query)
        } else {
            if (!enableSearchBooks) {
                isStop = false
                enableSearchBooks = true
            }
            getBooks(query)
        }

    }

    private fun getBooks(query: String) {
        val books = mutableListOf<Book>()
        var currentPage = 1

        view.resetResult()
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

    private fun getBook(id: String) {
        view.showLoading()
        repository.getRemoteBook(id, object : OnDataLoadCallBack<Book> {
            override fun onSuccess(data: Book) {
                view.resetResult()
                view.showSearchResult(mutableListOf(data))
                view.hideLoading()
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

    companion object {
        const val REGEX_ID_BOOK = "[0-9]{13}"
    }
}
