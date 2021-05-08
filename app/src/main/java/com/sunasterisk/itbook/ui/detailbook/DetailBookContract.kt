package com.sunasterisk.itbook.ui.detailbook

import com.sunasterisk.itbook.base.BasePresenter
import com.sunasterisk.itbook.base.BaseView
import com.sunasterisk.itbook.data.model.Book

class DetailBookContract {
    interface View : BaseView {
        fun showBook(book: Book)
        fun showSimilarBooks(books: List<Book>)
        fun showFavorite(isFavorite: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getRemoteBook(id: String)
        fun getLocalBook(id: String)
        fun getSimilarBooks(title: String)
        fun getFavorite(id: String)
        fun setFavorite(isFavorite: Boolean)
    }
}
