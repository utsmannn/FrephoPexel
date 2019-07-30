package com.utsman.frepho.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.utsman.frepho.R
import com.utsman.frepho.base.categories
import com.utsman.frepho.base.injectCategoryViewModel
import com.utsman.frepho.base.toVisibility
import com.utsman.frepho.data.Category
import com.utsman.frepho.data.NetworkState
import com.utsman.frepho.data.Status
import com.utsman.frepho.ui.adapter.CategoryAdapter
import kotlinx.android.synthetic.main.layout_main_list.*

class HomeFragment : Fragment() {

    private val categoryViewModel by lazy { injectCategoryViewModel() }
    private val categoryAdapter = CategoryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_main_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        main_recycler_view.adapter = categoryAdapter

        categories.map { cat ->
            categoryViewModel.getCoverCategory(cat).observe(this, Observer { url ->
                val category = Category(cat, url)
                categoryAdapter.addCategory(category)
            })
        }

        categoryViewModel.getNetworkState().observe(this, Observer { network ->
            error_text_view.visibility = toVisibility(network?.status == Status.FAILED)
            error_text_view.text = network?.msg
        })

    }
}