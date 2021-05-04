package com.example.itbook.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itbook.R
import com.example.itbook.data.model.Book
import com.example.itbook.utils.loadImageFromUri
import kotlinx.android.synthetic.main.item_book.view.*

class BookAdapter(
    private val currentCategory: Int = 0,
    private val itemClickListener: (Int, Int) -> Unit
) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    private val books = mutableListOf<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false),
        currentCategory, itemClickListener
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(books[position])
    }

    override fun getItemCount(): Int = books.size

    fun updateData(books: List<Book>) {
        this.books.run {
            clear()
            addAll(books)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(
        itemView: View,
        currentCategory: Int,
        itemClick: (Int, Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { itemClick(currentCategory, adapterPosition) }
        }

        fun bindData(book: Book) {
            book.apply {
                itemView.textTitleBook.text = title
                itemView.imageBook.loadImageFromUri(image)
            }
        }
    }
}
