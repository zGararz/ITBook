package com.example.itbook.ui.search

import com.example.itbook.data.model.Book
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.utils.OnDataLoadCallBack
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Test

class SearchPresenterTest {
    private val repository = mockk<BooksRepository>()
    private val view = mockk<SearchContract.View>(relaxed = true)
    private val callback = slot<OnDataLoadCallBack<List<Book>>>()
    private val presenter = SearchPresenter(view, repository)

    @Test
    fun `getSearchBook return fail`() {
        val title = "Android"
        val error = Exception()

        every {
            repository.getRemoteBooks(title, 1, capture(callback))
        } answers {
            callback.captured.onFail(error)
        }

        presenter.getSearchBook(title)
        verify {
            view.showError(error)
        }
    }

    @Test
    fun `getSearchBook return success with null data`() {
        val title = "Android"
        val books = mutableListOf<Book>()

        every {
            repository.getRemoteBooks(title, 1, capture(callback))
        } answers {
            callback.captured.onSuccess(books)
        }

        presenter.getSearchBook(title)
        verify {
            view.showSearchResult(books)
        }
    }

    @Test
    fun `getBooks return success with data and isStop = false`() {
        val title = "Android"
        val books = mutableListOf<Book>()
        val book = mockk<Book>()
        books.add(book)

        every {
            repository.getRemoteBooks(title, 1, capture(callback))
        } answers {
            callback.captured.onSuccess(books)
        }
        every {
            repository.getRemoteBooks(title, 2, capture(callback))
        } answers {
            null
        }
        every { book.title } returns "Android Basic"

        presenter.getSearchBook(title)
        verify {
            view.showSearchResult(books)
        }
    }

    @Test
    fun `getBooks return success with data and isStop = true`() {
        val title = "Android"
        val books = mutableListOf<Book>()
        val book = mockk<Book>()
        books.add(book)

        every {
            repository.getRemoteBooks(title, 1, capture(callback))
        } answers {
            callback.captured.onSuccess(books)
        }
        every { book.title } returns "Android Basic"

        presenter.stop()
        presenter.getSearchBook(title)
        verify {
            view.showSearchResult(books)
        }
    }
}
