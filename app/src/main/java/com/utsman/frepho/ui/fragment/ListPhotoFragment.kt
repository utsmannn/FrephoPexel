package com.utsman.frepho.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.utsman.frepho.R
import com.utsman.frepho.base.injectMainViewModel
import com.utsman.frepho.base.logi
import com.utsman.frepho.ui.adapter.MainPagedAdapter
import kotlinx.android.synthetic.main.layout_main_list.*

class ListPhotoFragment : Fragment() {

    private val mainViewModel by lazy { injectMainViewModel() }
    private val pagedAdapter = MainPagedAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_main_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (pagedAdapter.getItemViewType(position)) {
                    R.layout.item_image -> 1
                    R.layout.item_loader -> 2
                    else -> 1
                }
            }
        }

        main_recycler_view.layoutManager = gridLayoutManager
        main_recycler_view.setItemViewCacheSize(100)
        main_recycler_view.adapter = pagedAdapter

        mainViewModel.getCurated().observe(this, Observer { data ->
            pagedAdapter.submitList(data)
            logi("aaaaaaaaa", data.snapshot().toString())
        })

        mainViewModel.getLoader().observe(this, Observer { networkState ->
            pagedAdapter.setNetworkState(networkState)
        })
    }
}