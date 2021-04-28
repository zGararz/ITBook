package com.example.itbook.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itbook.R
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(
    private val itemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val categories = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_category,
            parent,
            false
        ),
        itemClickListener
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    fun updateData(categories: List<String>) {
        this.categories.run {
            clear()
            addAll(categories)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(
        itemView: View,
        itemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { itemClick(adapterPosition) }
        }

        fun bindData(category: String) {
            itemView.textCategory.text = category
        }
    }
}
