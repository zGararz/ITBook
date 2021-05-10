package com.example.itbook.ui.home

import com.example.itbook.data.model.Book
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.utils.OnDataLoadCallBack
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Test

class HomePresenterTest {
    private val categories =
        listOf("Android", "Android", "Android", "Android", "Android", "Android")
    private val repository = mockk<BooksRepository>()
    private val view = mockk<HomeContract.View>(relaxed = true)
    private val callback = slot<OnDataLoadCallBack<List<Book>>>()
    private val presenter = HomePresenter(view, repository, categories)

    @Test
    fun `getPreviewCategories return fail`() {
        val category = "Android"
        val error = Exception()

        every {
            repository.getRemoteBooks(category, capture(callback))
        } answers {
            callback.captured.onFail(error)
        }
        every {
            repository.getNewsBook(capture(callback))
        } answers {
            callback.captured.onFail(error)
        }

        presenter.getPreviewCategories()
        verify {
            view.showError(error)
        }
    }

    @Test
    fun `getPreviewCategories return success`() {
        val category = "Android"
        val books = listOf<Book>()

        every {
            repository.getRemoteBooks(category, capture(callback))
        } answers {
            callback.captured.onSuccess(books)
        }
        every {
            repository.getNewsBook(capture(callback))
        } answers {
            callback.captured.onSuccess(books)
        }

        presenter.getPreviewCategories()
        verify {
            view.hideLoading()
        }
    }

    @Test
    fun `getPreviewCategories return success and fail`() {
        val category = "Android"
        val error = Exception()
        val books = listOf<Book>()

        every {
            repository.getRemoteBooks(category, capture(callback))
        } answers {
            callback.captured.onFail(error)
        }
        every {
            repository.getNewsBook(capture(callback))
        } answers {
            callback.captured.onSuccess(books)
        }

        presenter.getPreviewCategories()
        verify {
            view.hideLoading()
        }
    }
}
