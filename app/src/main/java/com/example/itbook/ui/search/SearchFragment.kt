package com.example.itbook.ui.search

import android.widget.EditText
import android.widget.TextView
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
import com.example.itbook.utils.closeKeyboard
import com.example.itbook.utils.openKeyboard
import com.example.itbook.utils.showError
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment(), SearchContract.View {
    override val layoutResource: Int = R.layout.fragment_search

    private var presenter: SearchPresenter? = null
    private var booksRepository: BooksRepository? = null
    private val previewBookAdapter: PreviewBookAdapter = PreviewBookAdapter(this::onBookClick)
    private var loadingDialogFragment: LoadingDialogFragment? = null
    private var books = mutableListOf<Book>()
    private var isFirstSearch = true

    override fun initViews() {
        editSearch.requestFocus()
        activity?.let { editSearch.openKeyboard(it) }
        recyclerSearchBooks.adapter = previewBookAdapter
        loadingDialogFragment = LoadingDialogFragment()
        loadingDialogFragment?.isCancelable = false
        isSetup = false
    }

    override fun initData() {
        activity?.let {
            booksRepository = BooksRepository.getInstance(
                BooksRemoteDataSource.getInstance(BookRemoteHandler()),
                BooksLocalDataSource.getInstance(BookDaoImp(BooksDatabase.getInstance(it)))
            )
        }
        presenter = booksRepository?.let { SearchPresenter(this, it) }
    }

    override fun initListeners() {
        editSearch.setOnEditorActionListener(onEditorActionListener())
        imageSearch.setOnClickListener { onSearchListener(editSearch) }
        imageCancelSearch.setOnClickListener { onCancelClickListener() }
    }

    override fun showSearchResult(books: List<Book>) {
        if (books.isNotEmpty()) {
            val booksSize = this.books.size - 1
            val insertCount = books.size - this.books.size

            previewBookAdapter.also {
                if (books.size == this.books.size) {
                    it.updateData(books.toMutableList())
                } else {
                    it.updateData(books.toMutableList(), booksSize, insertCount)
                }
            }
            this.books = books.toMutableList()
        } else {
            previewBookAdapter.apply {
                updateData(mutableListOf())
                notifyDataSetChanged()
            }
        }
    }

    override fun resetResult() {
        previewBookAdapter.apply {
            updateData(mutableListOf())
        }
    }

    override fun showError(error: Exception?) {
        activity?.showError(resources.getString(R.string.error_search))
    }

    override fun showLoading(isShow: Boolean) {
        loadingDialogFragment?.let {
            if (isShow) {
                activity?.let { activity -> it.show(activity.supportFragmentManager, "") }
            } else {
                it.dismiss()
            }
        }
    }

    private fun onSearchListener(search: EditText) {
        val query = search.text.toString()
        val titleSearch = "\"${search.text}\" ${resources.getString(R.string.text_search_result)}"

        textTitleSearch.text = titleSearch
        if (isFirstSearch) {
            isFirstSearch = false
        } else {
            presenter?.stop()
        }
        presenter?.getSearchBook(query)
        activity?.let { search.closeKeyboard(it) }
    }

    private fun onEditorActionListener(): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { _, _, _ ->
            onSearchListener(editSearch)
            true
        }
    }

    private fun onBookClick(position: Int) {
        val fragment = DetailBookFragment().apply {
            arguments = bundleOf(
                Book.ISBN13 to books[position].isbn13
            )
        }
        addFragment(fragment)
    }

    private fun onCancelClickListener() = activity?.supportFragmentManager?.popBackStack()

    override fun onStop() {
        super.onStop()
        presenter?.stop()
    }
}
