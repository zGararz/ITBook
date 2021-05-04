package com.example.itbook.ui.detailcategory

import com.example.itbook.data.model.Book
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.data.source.remote.API.APIQuery
import com.example.itbook.utils.OnDataLoadCallBack
import java.lang.Exception

class DetailCategoryPresenter(
    private val view: DetailCategoryFragment,
    private val repository: BooksRepository,
    private val category: String
) : DetailCategoryContract.Presenter {

    private val books = mutableListOf<Book>()
    private var currentPage = 1
    private var stop = false

    override fun start() {
        val newBook = "New"

        if (category == newBook) {
            getNewBooks()
        } else {
            getBooks(category)
        }
    }

    override fun stop() {
        stop = true
    }

    override fun getBooks(category: String) {
        view?.showLoading(true)
        repository.getRemoteBooks(
            APIQuery.queryBooks(category, currentPage),
            object : OnDataLoadCallBack<List<Book>> {
                override fun onSuccess(data: List<Book>) {
                    books.addAll(data)
                    view?.showBooks(books)
                    view?.showLoading(false)

                    if (view != null && !data.isEmpty() && !stop) {
                        currentPage++
                        repository.getRemoteBooks(APIQuery.queryBooks(category, currentPage), this)
                    }
                }

                override fun onFail(message: Exception?) {
                    view?.showError(message)
                    view?.showLoading(false)
                }
            })
    }

    private fun getNewBooks() {
        view?.showLoading(true)
        repository.getRemoteBooks(
            APIQuery.queryNews(),
            object : OnDataLoadCallBack<List<Book>> {
                override fun onSuccess(data: List<Book>) {
                    books.addAll(data)
                    view?.showBooks(books)
                    view?.showLoading(false)
                }

                override fun onFail(message: Exception?) {
                    view?.showError(message)
                    view?.showLoading(false)
                }
            })
    }
}
