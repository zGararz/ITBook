package com.example.itbook.ui.similar

import com.example.itbook.base.BasePresenter
import com.example.itbook.base.BaseView
import com.example.itbook.data.model.Book

class SimilarBooksContract {
    interface View : BaseView {
        fun showBooks(book: List<Book>)
    }

    interface Presenter : BasePresenter {
        fun getBooks(title: String)
        fun stop()
    }
}
