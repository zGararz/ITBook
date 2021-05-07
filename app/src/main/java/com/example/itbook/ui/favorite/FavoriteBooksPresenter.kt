package com.example.itbook.ui.favorite

import com.example.itbook.data.model.Book
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.utils.OnDataLoadCallBack

class FavoriteBooksPresenter(
    private val view: FavoriteBooksContract.View,
    private val repository: BooksRepository
) : FavoriteBooksContract.Presenter {

    override fun start() = getFavoriteBooks()

    override fun getFavoriteBooks() {
        view.showLoading()
        repository.getAllBooks(object : OnDataLoadCallBack<List<Book>> {
            override fun onSuccess(data: List<Book>) {
                view.showFavoriteBooks(data)
                view.hideLoading()
            }

            override fun onFail(message: Exception?) {
                view.showError(message)
                view.hideLoading()
            }
        })
    }
}
