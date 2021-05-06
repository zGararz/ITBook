package com.example.itbook.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itbook.R
import com.example.itbook.data.model.Book
import com.example.itbook.utils.loadImageFromUri
import kotlinx.android.synthetic.main.item_preview_book.view.*

class PreviewBookAdapter(
    private val itemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<PreviewBookAdapter.ViewHolder>() {

    private val books = mutableListOf<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_preview_book,
            parent,
            false
        ),
        itemClickListener
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(books[position])
    }

    override fun getItemCount(): Int = books.size

    fun updateData(books: MutableList<Book>) {
        this.books.run {
            clear()
            addAll(books)
            notifyDataSetChanged()
        }
    }

    fun updateData(books: MutableList<Book>, start: Int, count: Int) {
        this.books.run {
            clear()
            addAll(books)
            notifyItemRangeInserted(start, count)
        }
    }

    class ViewHolder(
        itemView: View,
        itemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { itemClick(adapterPosition) }
        }

        fun bindData(book: Book) {
            itemView.apply {
                textTitlePreviewBook.text = book.title
                textSubtitlePreviewBook.text = book.subtitle
                imagePreviewBook.loadImageFromUri(book.image)
            }
        }
    }
}
