package com.example.itbook.ui.home

import com.example.itbook.data.model.Book
import com.example.itbook.data.model.PreviewCategory
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.data.source.remote.API.APIQuery
import com.example.itbook.utils.OnDataLoadCallBack

class HomePresenter(
    private val view: HomeContract.View,
    private val repository: BooksRepository,
    private val categories: List<String>
) : HomeContract.Presenter {

    private val previewCategories = mutableListOf<PreviewCategory>()
    private var error = ""

    override fun start() {
        getPreviewCategories()
    }

    override fun getPreviewCategories() {
        getNewBooks()
        getBooksByCategories()
    }

    private fun getNewBooks() {
        view.showLoading(true)
        repository.getRemoteBooks(APIQuery.queryNews(), object : OnDataLoadCallBack<List<Book>> {
            override fun onSuccess(data: List<Book>) {
                previewCategories.add(
                    PreviewCategory(
                        categories[0],
                        data.toMutableList()
                    )
                )
                view.showPreviewCategories(previewCategories)
            }

            override fun onFail(message: String) {
                error = message
                view.showError(error)
                view.showLoading(false)
            }
        })
    }

    private fun getBooksByCategories() {
        val sizePreviewCategories = 5
        var categoryPosition = categories.size - 2

        while (categoryPosition >= categories.size - sizePreviewCategories) {
            categoryPosition--

            val previewCategory = PreviewCategory(categories[categoryPosition], mutableListOf())
            repository.getRemoteBooks(
                APIQuery.queryBooks(categories[categoryPosition]),
                object : OnDataLoadCallBack<List<Book>> {
                    override fun onSuccess(data: List<Book>) {
                        previewCategory.books = data.toMutableList()
                        previewCategories.add(
                            previewCategory
                        )
                        view.showPreviewCategories(previewCategories)
                        view.showLoading(false)
                    }

                    override fun onFail(message: String) {
                        if (message != error) {
                            error = message
                            view.showError(error)
                            view.showLoading(false)
                        }
                    }
                }
            )
        }
    }
}
