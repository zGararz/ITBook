package com.example.itbook.ui.home

import com.example.itbook.base.BasePresenter
import com.example.itbook.base.BaseView
import com.example.itbook.data.model.PreviewCategory

class HomeContract {
    interface View : BaseView {
        fun showCategories(categories: List<String>)
        fun showPreviewCategories(previewCategories: List<PreviewCategory>)
    }

    interface Presenter : BasePresenter {
        fun getPreviewCategories()
    }
}
