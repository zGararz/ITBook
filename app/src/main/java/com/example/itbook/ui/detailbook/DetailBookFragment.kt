package com.example.itbook.ui.detailbook

import android.content.Intent
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
import com.example.itbook.ui.adapter.BookAdapter
import com.example.itbook.ui.dialog.LoadingDialogFragment
import com.example.itbook.utils.LinkBuilder
import com.example.itbook.utils.loadImageFromUri
import com.example.itbook.utils.showError
import kotlinx.android.synthetic.main.fragment_detail_book.*
import kotlinx.android.synthetic.main.preview_category.view.*
import org.json.JSONException

class DetailBookFragment : BaseFragment(), DetailBookContract.View {
    override val layoutResource: Int = R.layout.fragment_detail_book

    private var presenter: DetailBookPresenter? = null
    private var loadingDialogFragment: LoadingDialogFragment? = null
    private val bookAdapter = BookAdapter(itemClickListener = this::onItemBookClickListener)
    private var books = mutableListOf<Book>()
    private var book: Book? = null
    private var isFavoriteBook = false
    private var isLocalBook = false

    override fun initViews() {
        val titleSimilarBook = resources.getString(R.string.title_similar_book)
        layoutPreviewCategory.apply {
            recyclerViewBooks.adapter = bookAdapter
            textPreviewCategory.text = titleSimilarBook
        }
        loadingDialogFragment = LoadingDialogFragment()
        loadingDialogFragment?.isCancelable = false
        isSetup = false
    }

    override fun initData() {
        var isbn13: String? = null
        arguments?.let {
            isLocalBook = it.getBoolean(STRING_LOCAL, false)
            isbn13 = it.getString(Book.ISBN13)
        }
        activity?.let {
            val bookRepository = BooksRepository.getInstance(
                BooksRemoteDataSource.getInstance(BookRemoteHandler()),
                BooksLocalDataSource.getInstance(BookDaoImp(BooksDatabase.getInstance(it)))
            )
            presenter =
                isbn13?.let { it1 -> DetailBookPresenter(this, bookRepository, it1, isLocalBook) }
            presenter?.start()
        }

    }

    override fun initListeners() {
        imageFavoriteDetailBook.setOnClickListener { onFavoriteClickListener() }
        imageBackDetailBook.setOnClickListener { onBackClickListener() }
        imageShareDetailBook.setOnClickListener { onShareClickListener() }
        layoutPreviewCategory.textPreviewCategory.setOnClickListener { onSimilarBooksClickListener() }
    }

    override fun showBook(book: Book) {
        book.apply {
            imageDetailBook.loadImageFromUri(image)
            textTitleDetailBook.text = title
            textAuthorDetailBook.text = authors
            textDescDetailBook.text = desc
            imageRatingDetailBook.setImageResource(getImageRating(rating))
            textPublisherDetailBook.text = publisher
            textLanguageDetailBook.text = language
            textPapersDetailBook.text = pages.toString()
            textYearDetailBook.text = year.toString()
            textIsbn10DetailBook.text = isbn10
            textIsbn13DetailBook.text = isbn13
            LinkBuilder().build(textLinkDetailBook, url)
        }
        this.book = book
    }

    override fun showSimilarBooks(books: List<Book>) {
        bookAdapter.updateData(books.toList())
        this.books = books.toMutableList()
    }

    override fun showFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            isFavoriteBook = true
            imageFavoriteDetailBook.setImageResource(R.drawable.ic_favorite)
        } else {
            isFavoriteBook = false
            imageFavoriteDetailBook.setImageResource(R.drawable.ic_un_favorite)
        }
    }

    override fun showError(error: Exception?) {
        var errorMessage = "Error"
        when (error) {
            is JSONException -> errorMessage =
                resources.getString(R.string.error_internet_not_connection)
        }
        activity?.showError(errorMessage)
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

    private fun getImageRating(rating: Int) = when (rating) {
        Book.RATING_0 -> R.drawable.ic_0_star
        Book.RATING_1 -> R.drawable.ic_1_star
        Book.RATING_2 -> R.drawable.ic_2_star
        Book.RATING_3 -> R.drawable.ic_3_star
        Book.RATING_4 -> R.drawable.ic_4_star
        Book.RATING_5 -> R.drawable.ic_5_star
        else -> R.drawable.ic_0_star
    }

    private fun onFavoriteClickListener() {
        if (isFavoriteBook) presenter?.setFavorite(false) else presenter?.setFavorite(true)
    }

    private fun onItemBookClickListener(currentCategory: Int, position: Int) {
        val fragment = DetailBookFragment()
        fragment.arguments = bundleOf(
            Book.ISBN13 to books[position].isbn13
        )
        addFragment(fragment)
    }

    private fun onBackClickListener() {
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun onShareClickListener() {
        book?.let {
            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, it.url)
                type = "text/plain"
            }

            startActivity(intent)
        }
    }

    private fun onSimilarBooksClickListener() {}

    companion object {
        const val STRING_LOCAL = "local"
    }
}
