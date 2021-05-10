package com.example.itbook.ui.similar

import androidx.core.os.bundleOf
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
import com.example.itbook.utils.showMessage
import kotlinx.android.synthetic.main.fragment_similar_book.*
import org.json.JSONException

class SimilarBooksFragment : BaseFragment(), SimilarBooksContract.View {
    override val layoutResource: Int = R.layout.fragment_similar_book

    private val category by lazy { arguments?.getString(BUNDLE_BOOK_TITLE) }
    private var presenter: SimilarBooksPresenter? = null
    private val previewBookAdapter = PreviewBookAdapter(this::onBookClick)
    private var books = mutableListOf<Book>()


    override fun initViews() {
        recyclerSimilarBooks.adapter = previewBookAdapter
    }

    override fun initData() {
        val booksRepository = activity?.let {
            BooksRepository.getInstance(
                BooksRemoteDataSource.getInstance(BookRemoteHandler()),
                BooksLocalDataSource.getInstance(BookDaoImp(BooksDatabase.getInstance(it)))
            )
        }

        presenter = booksRepository?.let { repository ->
            SimilarBooksPresenter(this, repository)
        }

        category?.let { presenter?.getBooks(it) }
    }

    override fun initListeners() {
        imageBackSimilarBooks.setOnClickListener { onBackClickListener() }
    }

    override fun showBooks(books: List<Book>) {
        val booksSize = this.books.size - 1
        val insertCount = books.size - this.books.size

        previewBookAdapter.updateData(books.toMutableList(), booksSize, insertCount)
        this.books = books.toMutableList()
    }

    override fun showError(error: Exception?) {
        error?.let {
            when (it) {
                is JSONException -> activity?.showMessage(resources.getString(R.string.error_load_data))
                else -> activity?.showMessage(error.toString())
            }
        }
    }

    private fun onBookClick(position: Int) {
        addFragment(
            DetailBookFragment.getInstance(books[position].isbn13, false),
            R.anim.anim_push_in,
            R.anim.anim_push_out
        )
    }

    private fun onBackClickListener() {
        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onStop() {
        super.onStop()
        presenter?.stop()
    }

    companion object {
        private const val BUNDLE_BOOK_TITLE = "BUNDLE_BOOK_TITLE"

        fun getInstance(title: String) = SimilarBooksFragment().apply {
            arguments = bundleOf(BUNDLE_BOOK_TITLE to title)
        }
    }
}
