package com.straucorp.datetimewidget.widget.pager

import androidx.fragment.app.Fragment

/**
 * Created by @author André Straube on 08/07/2021
 */
interface IPagerAdapter {
    fun getCount(): Int
    fun getItem(position: Int): Fragment
    fun getPageTitle(position: Int): CharSequence
}