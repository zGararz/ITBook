package com.example.itbook.ui.detailcategory

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.itbook.data.model.Book
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.data.source.remote.API.APIQuery
import com.example.itbook.utils.OnDataLoadCallBack

class DetailCategoryPresenter(
    private val view: DetailCategoryFragment,
    private val repository: BooksRepository,
    private val category: String
) : DetailCategoryContract.Presenter {

    private val books = mutableListOf<Book>()
    private var currentPage = 1
    private var isStop = false

    @RequiresApi(Build.VERSION_CODES.R)
    override fun start() {
        val newBook = "New"

        if (category == newBook) {
            getNewBooks()
        } else {
            getBooks(category)
        }
    }

    override fun stop() {
        isStop = true
    }


    @RequiresApi(Build.VERSION_CODES.R)
    override fun getBooks(category: String) {
        view.showLoading()
        repository.getRemoteBooks(
            category, currentPage,
            object : OnDataLoadCallBack<List<Book>> {
                override fun onSuccess(data: List<Book>) {
                    books.addAll(data)
                    view.showBooks(books)
                    view.hideLoading()

                    if (view != null && !data.isEmpty() && !isStop) {
                        currentPage++
                        repository.getRemoteBooks(APIQuery.queryBooks(category, currentPage), this)
                    }
                }

                override fun onFail(message: Exception?) {
                    view.showError(message)
                    view.hideLoading()
                }
            })
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getNewBooks() {
        view.showLoading()
        repository.getNewsBook(object : OnDataLoadCallBack<List<Book>> {
            override fun onSuccess(data: List<Book>) {
                books.addAll(data)
                view.showBooks(books)
                view.hideLoading()
            }

            override fun onFail(message: Exception?) {
                view.showError(message)
                view.hideLoading()
            }
        })
    }
}
