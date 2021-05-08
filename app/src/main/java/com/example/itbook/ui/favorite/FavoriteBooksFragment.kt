package com.example.itbook.ui.favorite

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import com.example.itbook.R
import com.example.itbook.base.BaseFragment
import com.example.itbook.data.model.Book
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.data.source.local.BooksLocalDataSource
import com.example.itbook.data.source.local.Database.BooksDatabase
import com.example.itbook.data.source.local.dao.BookDaoImp
import com.example.itbook.data.source.remote.BookRemoteHandler
import com.example.itbook.data.source.remote.BooksRemoteDataSource
import com.example.itbook.ui.adapter.PreviewBookAdapter
import com.example.itbook.ui.detailbook.DetailBookFragment
import com.example.itbook.utils.closeKeyboard
import com.example.itbook.utils.showMessage
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteBooksFragment : BaseFragment(), FavoriteBooksContract.View {
    override val layoutResource = R.layout.fragment_favorite

    private var presenter: FavoriteBooksPresenter? = null
    private val previewBookAdapter = PreviewBookAdapter(this::onBookClick)
    private var books = mutableListOf<Book>()

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        @SuppressLint("DefaultLocale")
        override fun afterTextChanged(s: Editable?) {
            previewBookAdapter.apply {
                if (s.toString().isEmpty()) {
                    updateData(books)
                    notifyDataSetChanged()

                } else {
                    updateData(filter(s.toString().toLowerCase(), books).toMutableList())
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun initViews() {
        recyclerFavoriteBooks.adapter = previewBookAdapter
        isSetup = false
    }

    override fun initData() {

        activity?.let {
            val booksRepository = BooksRepository.getInstance(
                BooksRemoteDataSource.getInstance(BookRemoteHandler()),
                BooksLocalDataSource.getInstance(BookDaoImp(BooksDatabase.getInstance(it)))
            )
            presenter = FavoriteBooksPresenter(this, booksRepository)
            presenter?.start()
        }
    }

    override fun initListeners() {
        editSearchFavoriteBooks.setOnEditorActionListener { _, _, _ ->
            activity?.let { editSearchFavoriteBooks.closeKeyboard(it) }
            true
        }
        editSearchFavoriteBooks.addTextChangedListener(textWatcher)
    }

    override fun showFavoriteBooks(books: List<Book>) {
        val booksCount = "${books.size} ${textBooksCount.text}"

        textBooksCount.text = booksCount
        previewBookAdapter.apply {
            updateData(books.toMutableList())
            notifyDataSetChanged()
        }
        this.books = books.toMutableList()
    }

    override fun showError(error: Exception?) {
        error?.let { activity?.showMessage(it.toString()) }
    }

    private fun onBookClick(position: Int) {
        addFragment(
            DetailBookFragment.getInstance(books[position].isbn13, true),
            R.anim.anim_push_in,
            R.anim.anim_push_out
        )
    }

    @SuppressLint("DefaultLocale")
    private fun filter(search: String, books: List<Book>): List<Book> {
        val filteredBooks = mutableListOf<Book>()
        for (book in books) {
            if (search in book.title.toLowerCase() || search in book.subtitle.toLowerCase()) {
                filteredBooks.add(book)
            }
        }

        return filteredBooks
    }
}
