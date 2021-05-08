package com.sunasterisk.itbook.ui.similar

import com.sunasterisk.itbook.base.BasePresenter
import com.sunasterisk.itbook.base.BaseView
import com.sunasterisk.itbook.data.model.Book

class SimilarBooksContract {
    interface View : BaseView {
        fun showBooks(book: List<Book>)
    }

    interface Presenter : BasePresenter {
        fun getBooks(title: String)
        fun stop()
    }
}
