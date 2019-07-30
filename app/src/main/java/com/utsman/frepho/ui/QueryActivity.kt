package com.utsman.frepho.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.utsman.frepho.R
import com.utsman.frepho.base.capitalizeFirstWord
import com.utsman.frepho.base.injectMainViewModel
import com.utsman.frepho.ui.adapter.MainPagedAdapter
import kotlinx.android.synthetic.main.activity_query.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class QueryActivity : AppCompatActivity() {

    private val mainViewModel by lazy { injectMainViewModel() }
    private val query by lazy { intent.getStringExtra("query") }
    private val pagedAdapter = MainPagedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)

        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (pagedAdapter.getItemViewType(position)) {
                    R.layout.item_image -> 1
                    R.layout.item_loader -> 2
                    else -> 1
                }
            }
        }

        query_recycler_view.layoutManager = gridLayoutManager
        query_recycler_view.setItemViewCacheSize(100)
        query_recycler_view.adapter = pagedAdapter

        toolbar.title = query.capitalizeFirstWord()

        mainViewModel.getSearch(query).observe(this, Observer {data ->
            pagedAdapter.submitList(data)
        })

        mainViewModel.getLoader().observe(this, Observer { network ->
            pagedAdapter.setNetworkState(network)
        })

    }
}