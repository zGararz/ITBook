package com.example.itbook.ui.search

import com.example.itbook.base.BasePresenter
import com.example.itbook.base.BaseView
import com.example.itbook.data.model.Book

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
