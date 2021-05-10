package com.example.itbook.ui.similar

import com.example.itbook.data.model.Book
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.data.source.remote.API.APIQuery
import com.example.itbook.utils.OnDataLoadCallBack

class SimilarBooksPresenter(
    private val view: SimilarBooksContract.View,
    private val repository: BooksRepository
) : SimilarBooksContract.Presenter {

    private var currentPage = 1
    private var isStop = false

    override fun start() {}

    override fun stop() {
        isStop = true
    }

    override fun getBooks(title: String) {
        val books = mutableListOf<Book>()

        view.showLoading()
        repository.getRemoteBooks(title, currentPage, object : OnDataLoadCallBack<List<Book>> {
            override fun onSuccess(data: List<Book>) {
                val start = if(data.first().title == title) 1 else 0
                val end = data.size -1

                books.addAll(data.subList(start, end))
                view.showBooks(books)
                view.hideLoading()

                if (data.isNotEmpty() && !isStop) {
                    currentPage++
                    repository.getRemoteBooks(title, currentPage, this)
                }
            }

            override fun onFail(message: Exception?) {
                view.showError(message)
                view.hideLoading()
            }
        })
    }
}
