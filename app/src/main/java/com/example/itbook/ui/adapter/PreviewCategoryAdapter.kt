package com.example.itbook.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itbook.R
import com.example.itbook.data.model.PreviewCategory
import kotlinx.android.synthetic.main.preview_category.view.*

class PreviewCategoryAdapter(
    private val categoryClickListener: (Int) -> Unit,
    private val bookClickListener: (Int, Int) -> Unit
) : RecyclerView.Adapter<PreviewCategoryAdapter.ViewHolder>() {

    private val previewCategories = mutableListOf<PreviewCategory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.preview_category,
            parent,
            false
        ),
        categoryClickListener
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookAdapter = BookAdapter(position, bookClickListener)
        holder.bindData(previewCategories[position], bookAdapter)
    }

    override fun getItemCount(): Int = previewCategories.size

    fun updateData(previewCategories: List<PreviewCategory>) {
        this.previewCategories.run {
            clear()
            addAll(previewCategories)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(
        itemView: View,
        itemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.textPreviewCategory.setOnClickListener { itemClick(adapterPosition) }
        }

        fun bindData(previewCategory: PreviewCategory, bookAdapter: BookAdapter) {
            previewCategory.apply {
                itemView.textPreviewCategory.text = name
                itemView.recyclerViewBooks.adapter = bookAdapter
                bookAdapter.updateData(books)
                bookAdapter.notifyDataSetChanged()
            }
        }
    }
}
