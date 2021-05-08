package com.sunasterisk.itbook.ui.detailcategory

import com.sunasterisk.itbook.base.BasePresenter
import com.sunasterisk.itbook.base.BaseView
import com.sunasterisk.itbook.data.model.Book

class DetailCategoryContract {
    interface View : BaseView {
        fun showBooks(book: List<Book>)
    }

    interface Presenter : BasePresenter {
        fun getBooks(category: String)
        fun stop()
    }
}
