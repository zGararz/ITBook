package com.example.itbook.ui.home

import com.example.itbook.data.model.Book
import com.example.itbook.data.model.PreviewCategory
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.utils.OnDataLoadCallBack

class HomePresenter(
    private val view: HomeContract.View,
    private val repository: BooksRepository,
    private val categories: List<String>
) : HomeContract.Presenter {

    private val previewCategories = mutableListOf<PreviewCategory>()
    private var error: Exception? = null

    override fun start() = getPreviewCategories()

    override fun getPreviewCategories() {
        getNewBooks()
        getBooksByCategories()
    }

    private fun getNewBooks() {
        view.showLoading()
        repository.getNewsBook(object : OnDataLoadCallBack<List<Book>> {
            override fun onSuccess(data: List<Book>) {
                val books = data.toMutableList()

                books.shuffle()
                previewCategories.add(PreviewCategory(categories[categories.size - 1], books))
                view.showPreviewCategories(previewCategories)
                view.hideLoading()
            }

            override fun onFail(message: Exception?) {
                error = message
                view.showError(error)
                view.hideLoading()
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
                categories[categoryPosition],
                object : OnDataLoadCallBack<List<Book>> {

                    override fun onSuccess(data: List<Book>) {
                        val books = data.toMutableList()

                        books.shuffle()
                        previewCategory.books = books
                        previewCategories.add(
                            previewCategory
                        )
                        view.showPreviewCategories(previewCategories)
                    }

                    override fun onFail(message: Exception?) {
                        if (message.toString() != error.toString()) {
                            error = message
                            view.hideLoading()
                        }
                    }
                }
            )
        }
    }
}
