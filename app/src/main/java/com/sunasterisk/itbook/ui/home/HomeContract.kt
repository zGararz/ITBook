package com.sunasterisk.itbook.ui.home

import com.sunasterisk.itbook.base.BasePresenter
import com.sunasterisk.itbook.base.BaseView
import com.sunasterisk.itbook.data.model.PreviewCategory

class HomeContract {
    interface View : BaseView {
        fun showCategories(categories: List<String>)
        fun showPreviewCategories(previewCategories: List<PreviewCategory>)
    }

    interface Presenter : BasePresenter {
        fun getPreviewCategories()
    }
}
