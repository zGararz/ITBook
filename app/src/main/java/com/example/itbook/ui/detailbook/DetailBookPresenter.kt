package com.example.itbook.ui.detailbook

import com.example.itbook.data.model.Book
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.utils.OnDataLoadCallBack

class DetailBookPresenter(
    private val view: DetailBookContract.View,
    private val repository: BooksRepository,
    private val isbn13: String,
    private val isLocalBook: Boolean
) : DetailBookContract.Presenter {

    private var book: Book? = null

    override fun start() {
        getFavorite(isbn13)

        if (isLocalBook) {
            getLocalBook(isbn13)
        } else getRemoteBook(isbn13)
    }

    override fun getRemoteBook(id: String) {
        view.showLoading()
        repository.getRemoteBook(id, object : OnDataLoadCallBack<Book> {
            override fun onSuccess(data: Book) {
                view.showBook(data)
                view.hideLoading()
                book = data

                book?.title?.let { getSimilarBooks(it) }
            }

            override fun onFail(message: Exception?) {
                view.showError(message)
                view.hideLoading()
            }
        })
    }

    override fun getLocalBook(id: String) {
        view.showLoading()
        repository.getBook(id, object : OnDataLoadCallBack<Book> {
            override fun onSuccess(data: Book) {
                view.showBook(data)
                view.hideLoading()
                book = data
            }

            override fun onFail(message: Exception?) {
                view.showError(message)
                view.hideLoading()
            }
        })
    }

    override fun getSimilarBooks(title: String) {
        repository.getRemoteBooks(title, object : OnDataLoadCallBack<List<Book>> {
            override fun onSuccess(data: List<Book>) {
                if (data.isNotEmpty()) {
                    val books = data.toMutableList()
                    books.removeAt(0)
                    books.shuffle()
                    view.showSimilarBooks(books)
                }
            }

            override fun onFail(message: Exception?) {
                view.showError(message)
            }
        })
    }

    override fun getFavorite(id: String) {
        repository.getBook(id, object : OnDataLoadCallBack<Book> {
            override fun onSuccess(data: Book) {
                view.showFavorite(true)
            }

            override fun onFail(message: Exception?) {
                view.showFavorite(false)
            }
        })
    }

    override fun setFavorite(isFavorite: Boolean) {
        if (isFavorite) addFavoriteBook() else removeFavoriteBook()
    }

    private fun addFavoriteBook() {
        book?.let {
            repository.insertBook(it, object : OnDataLoadCallBack<Long> {
                override fun onSuccess(data: Long) {
                    if (data > 0) {
                        view.showFavorite(true)
                    } else {
                        view.showFavorite(false)
                    }
                }

                override fun onFail(message: Exception?) {
                    view.showFavorite(false)
                }
            })
        }
    }

    private fun removeFavoriteBook() {
        book?.let {
            repository.deleteBook(isbn13, object : OnDataLoadCallBack<Int> {
                override fun onSuccess(data: Int) {
                    if (data > 0) {
                        view.showFavorite(false)
                    } else {
                        view.showFavorite(true)
                    }
                }

                override fun onFail(message: Exception?) {
                    view.showFavorite(true)
                }
            })
        }
    }
}
