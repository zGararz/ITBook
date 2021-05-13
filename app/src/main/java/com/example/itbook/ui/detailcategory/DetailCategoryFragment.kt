package com.example.itbook.ui.detailcategory

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.itbook.ui.search.SearchFragment
import com.example.itbook.utils.showMessage
import kotlinx.android.synthetic.main.fragment_detail_category.*
import org.json.JSONException

class DetailCategoryFragment() : BaseFragment(),
    DetailCategoryContract.View {
    override val layoutResource: Int = R.layout.fragment_detail_category

    private var category: String? = null
    private var presenter: DetailCategoryPresenter? = null
    private val previewBookAdapter = PreviewBookAdapter(this::onBookClick)
    private var books = mutableListOf<Book>()


    override fun initViews() {
        recyclerPreviewCategory.adapter = previewBookAdapter
        textTitleDetailCategory.text = category
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun initData() {
        arguments?.let {
            category = it.getString(BUNDLE_BOOK_CATEGORY)
        }

        val booksRepository = activity?.let {
            BooksRepository.getInstance(
                BooksRemoteDataSource.getInstance(BookRemoteHandler()),
                BooksLocalDataSource.getInstance(BookDaoImp(BooksDatabase.getInstance(it)))
            )
        }

        category?.let {
            presenter = booksRepository?.let { repository ->
                DetailCategoryPresenter(this, repository, it)
            }
        }
        presenter?.start()

    }

    override fun initListeners() {
        imageBackDetailCategory.setOnClickListener { onBackClickListener() }
        imageSearch.setOnClickListener { addFragment(SearchFragment()) }
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
        private const val BUNDLE_BOOK_CATEGORY = "BUNDLE_BOOK_CATEGORY"

        fun getInstance(category: String) = DetailCategoryFragment().apply {
            arguments = bundleOf(BUNDLE_BOOK_CATEGORY to category)
        }
    }
}
