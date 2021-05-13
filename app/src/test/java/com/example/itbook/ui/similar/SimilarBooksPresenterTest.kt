package com.example.itbook.ui.similar

import com.example.itbook.data.model.Book
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.utils.OnDataLoadCallBack
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Test

class SimilarBooksPresenterTest {
    private val repository = mockk<BooksRepository>()
    private val view = mockk<SimilarBooksContract.View>(relaxed = true)
    private val callback = slot<OnDataLoadCallBack<List<Book>>>()
    private val presenter = SimilarBooksPresenter(view, repository)

    @Test
    fun `getBooks return fail`() {
        val title = "Android"
        val error = Exception()

        every {
            repository.getRemoteBooks(title, 1, capture(callback))
        } answers {
            callback.captured.onFail(error)
        }

        presenter.getBooks(title)
        verify {
            view.showError(error)
        }
    }

    @Test
    fun `getBooks return success with first book is'n the same title`() {
        val title = "Android"
        val books = mutableListOf<Book>()
        val book = mockk<Book>()
        val book2 = mockk<Book>()
        books.add(book)
        books.add(book2)

        every {
            repository.getRemoteBooks(title, 1, capture(callback))
        } answers {
            callback.captured.onSuccess(books)
            books.removeAt(1)
        }

        every {
            repository.getRemoteBooks(title, 2, capture(callback))
        } answers {
            null
        }
        every { book.title } returns "IOS"

        presenter.getBooks(title)
        verify {
            view.showBooks(books)
        }
    }


    @Test
    fun `getBooks return success with first book is the same title`() {
        val title = "Android"
        val books = mutableListOf<Book>()
        val book = mockk<Book>()
        val book2 = mockk<Book>()
        books.add(book)
        books.add(book2)

        every {
            repository.getRemoteBooks(title, 1, capture(callback))
        } answers {
            callback.captured.onSuccess(books)
            books.removeAt(0)
            books.removeAt(0)
        }

        every {
            repository.getRemoteBooks(title, 2, capture(callback))
        } answers {
            null
        }
        every { book.title } returns "Android"
        every { book2.title } returns "IOS"

        presenter.getBooks(title)
        verify {
            view.showBooks(books)
        }
    }


    @Test
    fun `getBooks return success and not call repo again`() {
        val title = "Android"
        val books = mutableListOf<Book>()
        val book = mockk<Book>()
        val book2 = mockk<Book>()
        books.add(book)
        books.add(book2)

        every {
            repository.getRemoteBooks(title, 1, capture(callback))
        } answers {
            callback.captured.onSuccess(books)
            books.removeAt(0)
            books.removeAt(0)
        }
        every { book.title } returns "Android"

        presenter.stop()
        presenter.getBooks(title)
        verify {
            view.showBooks(books)
        }
    }
}
