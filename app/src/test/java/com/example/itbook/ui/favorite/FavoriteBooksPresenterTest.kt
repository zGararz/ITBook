package com.example.itbook.ui.favorite

import com.example.itbook.data.model.Book
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.utils.OnDataLoadCallBack
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Test

class FavoriteBooksPresenterTest {

    private val repository = mockk<BooksRepository>()
    private val view = mockk<FavoriteBooksContract.View>(relaxed = true)
    private val callback = slot<OnDataLoadCallBack<List<Book>>>()
    private val presenter = FavoriteBooksPresenter(view, repository)

    @Test
    fun `getFavoriteBooks return success`() {
        val books = listOf<Book>()

        every {
            repository.getAllBooks(capture(callback))
        } answers {
            callback.captured.onSuccess(books)
        }

        presenter.getFavoriteBooks()
        verify {
            view.showFavoriteBooks(books)
        }
    }

    @Test
    fun `getFavoriteBooks return fail`() {
        val error = Exception()

        every {
            repository.getAllBooks(capture(callback))
        } answers {
            callback.captured.onFail(error)
        }

        presenter.getFavoriteBooks()
        verify {
            view.showError(error)
        }
    }

}
