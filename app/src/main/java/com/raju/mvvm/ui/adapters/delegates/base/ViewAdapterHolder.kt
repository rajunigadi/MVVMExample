package com.raju.mvvm.ui.adapters.delegates.base

interface ViewAdapterHolder<T> {
    fun setData(position: Int, data: T)
}