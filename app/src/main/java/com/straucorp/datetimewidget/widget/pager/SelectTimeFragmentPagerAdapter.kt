package com.straucorp.datetimewidget.widget.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by @author André Straube on 08/07/2021
 */
class SelectTimeFragmentPagerAdapter internal constructor(
    private val listItems: List<String>,
    private val fragmentManager: FragmentActivity,
    private val creatorFragment: (String) -> Fragment
) : FragmentStateAdapter(fragmentManager), IPagerAdapter {

    override fun getItemCount(): Int = listItems.size

    // Create the fragment to display for that page
    override fun createFragment(position: Int): Fragment = creatorFragment(listItems[position])

    // Returns the page title for the top indicator
    override fun getPageTitle(position: Int): CharSequence = listItems[position]

    // Compatibilidade com Pager V1
    override fun getCount(): Int = itemCount
    override fun getItem(position: Int): Fragment = createFragment(position)
}