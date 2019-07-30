package com.utsman.frepho.ui.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utsman.frepho.R
import com.utsman.frepho.base.layoutInflater
import com.utsman.frepho.base.loadUrlBlur
import com.utsman.frepho.data.Category
import com.utsman.frepho.ui.QueryActivity
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter : RecyclerView.Adapter<CategoryViewHolder>() {
    private val categoryList: MutableList<Category> = mutableListOf()

    fun addCategory(category: Category) {
        categoryList.add(category)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
            CategoryViewHolder(layoutInflater(parent, R.layout.item_category))

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.bind(category)
    }
}


class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(category: Category) = itemView.run {
        image_view_cover_category.loadUrlBlur(category.url)
        text_view_title_category.text = category.query.capitalize()

        setOnClickListener {
            val intentToActivityQuery = Intent(context, QueryActivity::class.java).apply {
                putExtra("query", category.query)
            }
            context.startActivity(intentToActivityQuery)
        }
    }
}