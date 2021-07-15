package com.straucorp.datetimewidget

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Created by @author André Straube on 08/07/2021
 */
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) = lazy(
    LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(layoutInflater)
}