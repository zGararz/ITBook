package com.example.itbook.ui.detailcategory

import com.example.itbook.data.model.Book
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.utils.OnDataLoadCallBack
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Test

class DetailCategoryPresenterTest {
    private val categoryAndroid = "Android"
    private val categoryNew = "New"
    private val repository = mockk<BooksRepository>()
    private val view = mockk<DetailCategoryFragment>(relaxed = true)
    private val callback = slot<OnDataLoadCallBack<List<Book>>>()

    @Test
    fun `getBooks return fail`() {
        val presenter = DetailCategoryPresenter(view, repository, categoryAndroid)
        val error = Exception()

        every {
            repository.getRemoteBooks(categoryAndroid, 1, capture(callback))
        } answers {
            callback.captured.onFail(error)
        }

        presenter.start()
        verify {
            view.showError(error)
        }
    }

    @Test
    fun `getBooks return success with null data`() {
        val presenter = DetailCategoryPresenter(view, repository, categoryAndroid)
        val books = listOf<Book>()

        every {
            repository.getRemoteBooks(categoryAndroid, 1, capture(callback))
        } answers {
            callback.captured.onSuccess(books)
        }

        presenter.start()
        verify {
            view.showBooks(books)
        }
    }

    @Test
    fun `getBooks return success with data and not call repo again`() {
        val presenter = DetailCategoryPresenter(view, repository, categoryAndroid)
        val books = mutableListOf<Book>()
        val book = mockk<Book>()
        val book2 = mockk<Book>()
        books.add(book)
        books.add(book2)

        every {
            repository.getRemoteBooks(categoryAndroid, 1, capture(callback))
        } answers {
            callback.captured.onSuccess(books)
        }

        presenter.stop()
        presenter.start()
        verify {
            view.showBooks(books)
        }
    }

    @Test
    fun `getBooks return success with data`() {
        val presenter = DetailCategoryPresenter(view, repository, categoryAndroid)
        val books = mutableListOf<Book>()
        val book = mockk<Book>()
        val book2 = mockk<Book>()
        books.add(book)
        books.add(book2)

        every {
            repository.getRemoteBooks(categoryAndroid, 1, capture(callback))
        } answers {
            callback.captured.onSuccess(books)
        }
        every {
            repository.getRemoteBooks(categoryAndroid, 2, capture(callback))
        } answers {
            null
        }

        presenter.start()
        verify {
            view.showBooks(books)
        }
    }

    @Test
    fun `getNews return fail`() {
        val presenter = DetailCategoryPresenter(view, repository, categoryNew)
        val error = Exception()

        every {
            repository.getNewsBook(capture(callback))
        } answers {
            callback.captured.onFail(error)
        }

        presenter.start()
        verify {
            view.showError(error)
        }
    }

    @Test
    fun `getNews return success`() {
        val presenter = DetailCategoryPresenter(view, repository, categoryNew)
        val books = listOf<Book>()

        every {
            repository.getNewsBook(capture(callback))
        } answers {
            callback.captured.onSuccess(books)
        }

        presenter.start()
        verify {
            view.showBooks(books)
        }
    }
}
