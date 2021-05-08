package com.sunasterisk.itbook.ui.search

import com.sunasterisk.itbook.base.BasePresenter
import com.sunasterisk.itbook.base.BaseView
import com.sunasterisk.itbook.data.model.Book

class SearchContract {
    interface View : BaseView {
        fun showSearchResult(books: List<Book>)
        fun resetResult()
    }

    interface Presenter : BasePresenter {
        fun getSearchBook(query: String)
        fun stop()
    }
}
