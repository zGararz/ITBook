package com.sunasterisk.itbook.ui.favorite

import com.sunasterisk.itbook.base.BasePresenter
import com.sunasterisk.itbook.base.BaseView
import com.sunasterisk.itbook.data.model.Book

class FavoriteBooksContract {
    interface View : BaseView {
        fun showFavoriteBooks(books: List<Book>)
    }

    interface Presenter : BasePresenter {
        fun getFavoriteBooks()
    }
}
