package com.example.itbook.ui.home

import com.example.itbook.R
import com.example.itbook.base.BaseFragment
import com.example.itbook.data.model.PreviewCategory
import com.example.itbook.data.repository.BooksRepository
import com.example.itbook.data.source.local.BooksLocalDataSource
import com.example.itbook.data.source.local.Database.BooksDatabase
import com.example.itbook.data.source.local.dao.BookDaoImp
import com.example.itbook.data.source.remote.BookRemoteHandler
import com.example.itbook.data.source.remote.BooksRemoteDataSource
import com.example.itbook.ui.adapter.CategoryAdapter
import com.example.itbook.ui.adapter.PreviewCategoryAdapter
import com.example.itbook.ui.detailbook.DetailBookFragment
import com.example.itbook.ui.detailcategory.DetailCategoryFragment
import com.example.itbook.ui.search.SearchFragment
import com.example.itbook.utils.showMessage
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONException

class HomeFragment : BaseFragment(), HomeContract.View {
    override val layoutResource = R.layout.fragment_home

    private var categories: List<String>? = null
    private var previewCategories = mutableListOf<PreviewCategory>()
    private var presenter: HomePresenter? = null
    private val categoryAdapter = CategoryAdapter(this::onItemCategoryClick)
    private val previewCategoryAdapter =
        PreviewCategoryAdapter(this::onItemPreviewCategoryClick, this::onItemBookClick)

    override fun initViews() {
        recyclerViewCategory.adapter = categoryAdapter
        recyclerViewPreviewCategory.adapter = previewCategoryAdapter
    }

    override fun initData() {
        var bookRepository: BooksRepository? = null
        categories = resources.getStringArray(R.array.categories).toList()
        activity?.let {
            bookRepository = BooksRepository.getInstance(
                BooksRemoteDataSource.getInstance(BookRemoteHandler()),
                BooksLocalDataSource.getInstance(BookDaoImp(BooksDatabase.getInstance(it)))
            )
        }

        categories?.let {
            bookRepository?.let { it1 ->
                presenter = HomePresenter(this, it1, it)
                showCategories(it)
            }
        }
        presenter?.start()
    }

    override fun initListeners() {
        textSearch.setOnClickListener { addFragment(SearchFragment()) }
    }

    override fun showCategories(categories: List<String>) {
        categories.let { categoryAdapter.updateData(it.toList()) }
    }

    override fun showPreviewCategories(previewCategories: List<PreviewCategory>) {
        previewCategoryAdapter.updateData(previewCategories)
        this.previewCategories = previewCategories.toMutableList()
    }

    override fun showError(error: Exception?) {
        error?.let {
            when (it) {
                is JSONException -> activity?.showMessage(resources.getString(R.string.error_load_data))
                else -> activity?.showMessage(error.toString())
            }
        }
    }

    private fun onItemCategoryClick(position: Int) {
        categories?.let {
            addFragment(
                DetailCategoryFragment.getInstance(it[position]),
                R.anim.anim_slide_up,
                R.anim.anim_slide_down
            )
        }
    }

    private fun onItemPreviewCategoryClick(position: Int) {
        addFragment(
            DetailCategoryFragment.getInstance(previewCategories[position].name),
            R.anim.anim_slide_up,
            R.anim.anim_slide_down
        )
    }

    private fun onItemBookClick(currentCategory: Int, position: Int) {
        addFragment(
            DetailBookFragment.getInstance(
                previewCategories[currentCategory].books[position].isbn13,
                false
            ),
            R.anim.anim_push_in, R.anim.anim_push_out
        )
    }
}
