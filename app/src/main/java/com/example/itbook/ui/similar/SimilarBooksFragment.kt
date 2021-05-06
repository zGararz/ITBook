package com.example.itbook.ui.similar

import androidx.core.os.bundleOf
import com.example.itbook.R
import com.example.itbook.base.BaseFragment

class SimilarBooksFragment : BaseFragment() {
    override val layoutResource: Int = R.layout.fragment_similar_book

    override fun initViews() {}

    override fun initData() {}

    override fun initListeners() {}

    companion object {
        private const val BUNDLE_BOOK_TITLE = "BUNDLE_BOOK_TITLE"

        fun getInstance(title: String) = SimilarBooksFragment().apply {
            arguments = bundleOf(BUNDLE_BOOK_TITLE to title)
        }
    }
}
