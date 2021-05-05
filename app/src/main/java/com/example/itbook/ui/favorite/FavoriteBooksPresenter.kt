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
        view.showLoading(true)
        repository.getAllBooks(object : OnDataLoadCallBack<List<Book>> {
            override fun onSuccess(data: List<Book>) {
                view.showFavoriteBooks(data)
                view.showLoading(false)
            }

            override fun onFail(message: Exception?) {
                view.showError(message)
                view.showLoading(false)
            }
        })
    }
}
