package com.utsman.frepho

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.utsman.frepho.ui.fragment.HomeFragment
import com.utsman.frepho.ui.fragment.ListPhotoFragment
import com.utsman.frepho.ui.adapter.MainViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val curatedFragment = ListPhotoFragment()

        val pagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(homeFragment, curatedFragment)
        main_pager.adapter = pagerAdapter

        toolbar.title = "Home"

        bottom_menu.setOnNavigationItemSelectedListener {menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    setupPager(0, "Home")
                    true
                }
                R.id.action_curated -> {
                    setupPager(1, "Curated Photos")
                    true
                }
                R.id.action_saved -> {

                    true
                }
                R.id.action_search -> {

                    true
                }
                R.id.action_more -> {

                    true
                }
                else -> false
            }
        }
    }

    private fun setupPager(position: Int, title: String) {
        main_pager.setCurrentItem(position, true)
        toolbar.title = title
    }
}
