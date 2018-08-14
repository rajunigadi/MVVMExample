package com.raju.mvvm.ui.adapters.delegates.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import javax.annotation.Nonnull

interface AdapterDelegate<T> {
    fun isForViewType(@Nonnull item: T, position: Int): Boolean
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(@Nonnull item: T, position: Int, holder: RecyclerView.ViewHolder)
    fun setDelegateClickListener(delegateClickListener: DelegateClickListener)
}