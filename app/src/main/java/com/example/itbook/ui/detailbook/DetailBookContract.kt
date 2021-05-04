package com.example.itbook.ui.detailbook

import com.example.itbook.base.BasePresenter
import com.example.itbook.base.BaseView
import com.example.itbook.data.model.Book

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
