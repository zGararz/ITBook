package com.example.itbook.ui.detailcategory

import com.example.itbook.base.BasePresenter
import com.example.itbook.base.BaseView
import com.example.itbook.data.model.Book

class DetailCategoryContract {
    interface View : BaseView {
        fun showBooks(book: List<Book>)
    }

    interface Presenter : BasePresenter {
        fun getBooks(category: String)
        fun stop()
    }
}
