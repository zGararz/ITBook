package com.example.itbook.ui.detailcategory

import android.widget.Toast
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
import com.example.itbook.ui.dialog.LoadingDialogFragment
import kotlinx.android.synthetic.main.fragment_detail_category.*
import org.json.JSONException

class DetailCategoryFragment() : BaseFragment(),
    DetailCategoryContract.View {
    override val layoutResource: Int = R.layout.fragment_detail_category

    private var category: String? = null
    private var presenter: DetailCategoryPresenter? = null
    private val previewBookAdapter = PreviewBookAdapter(this::onBookClick)
    private var loadingDialogFragment: LoadingDialogFragment? = null
    private var books = mutableListOf<Book>()


    override fun initViews() {
        recyclerPreviewCategory.adapter = previewBookAdapter
        loadingDialogFragment = LoadingDialogFragment()
        loadingDialogFragment?.isCancelable = false
    }

    override fun initData() {
        arguments?.let {
            category = it.getString(Book.CATEGORY)
            textTitleDetailCategory.text = category
        }
        activity?.let {
            val booksRepository = BooksRepository.getInstance(
                BooksRemoteDataSource.getInstance(BookRemoteHandler()),
                BooksLocalDataSource.getInstance(BookDaoImp(BooksDatabase.getInstance(it)))
            )

            category?.let { presenter = DetailCategoryPresenter(this, booksRepository, it) }
            presenter?.start()
        }
    }

    override fun initListeners() {
        imageBackDetailCategory.setOnClickListener { onBackClickListener() }
    }

    override fun showBooks(books: List<Book>) {
        val booksSize = this.books.size - 1
        val insertCount = books.size - this.books.size

        previewBookAdapter.apply {
            updateData(books.toMutableList())
            notifyItemRangeInserted(booksSize, insertCount)
        }
        this.books = books.toMutableList()
    }

    override fun showError(error: Exception?) {
        var errorMessage = ""
        when (error) {
            is JSONException -> errorMessage = resources.getString(R.string.error_internet_not_connection)
        }
        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun showLoading(isShow: Boolean) {
        loadingDialogFragment?.let {
            if (isShow) {
                activity?.let { it1 -> it.show(it1.supportFragmentManager, "") }
            } else {
                it.dismiss()
            }
        }
    }

    private fun onBookClick(position: Int) {
        val fragment = DetailBookFragment()

        fragment.arguments = bundleOf(
            Book.ISBN13 to books[position].isbn13
        )
        addFragment(fragment)
    }

    private fun onBackClickListener() {
        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onStop() {
        super.onStop()
        presenter?.stop()
    }
}
