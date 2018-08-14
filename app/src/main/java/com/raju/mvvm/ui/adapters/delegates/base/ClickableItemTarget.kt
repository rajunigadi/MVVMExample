package com.raju.mvvm.ui.adapters.delegates.base

interface ClickableItemTarget<T> {
    fun setOnItemClickListener(listener: ItemClickListener<T>)
}