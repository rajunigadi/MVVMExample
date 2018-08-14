package com.raju.mvvm.ui.adapters.delegates.base

import android.view.View

interface ItemClickListener<T> {
    fun onItemClick(view: View, position: Int, item: T)
}