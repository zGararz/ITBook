package com.sunasterisk.itbook.ui.favorite

import com.sunasterisk.itbook.data.model.Book
import com.sunasterisk.itbook.data.repository.BooksRepository
import com.sunasterisk.itbook.utils.OnDataLoadCallBack

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
