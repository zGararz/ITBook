package com.example.itbook.ui.favorite

import com.example.itbook.base.BasePresenter
import com.example.itbook.base.BaseView
import com.example.itbook.data.model.Book

class FavoriteBooksContract {
    interface View : BaseView {
        fun showFavoriteBooks(books: List<Book>)
    }

    interface Presenter : BasePresenter {
        fun getFavoriteBooks()
    }
}
