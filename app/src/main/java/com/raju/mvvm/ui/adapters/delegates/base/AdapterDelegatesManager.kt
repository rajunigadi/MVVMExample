package com.raju.mvvm.ui.adapters.delegates.base

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import javax.annotation.Nonnull

class AdapterDelegatesManager<T> {

    internal val FALLBACK_DELEGATE_VIEW_TYPE = Integer.MAX_VALUE - 1
    val delegates: SparseArrayCompat<AdapterDelegate<T>> = SparseArrayCompat()
    lateinit var fallbackDelegate: AdapterDelegate<T>

    fun addDelegate(@Nonnull delegate: AdapterDelegate<T>): AdapterDelegatesManager<T> {
        var viewType = delegates.size()
        while (delegates.get(viewType) != null) {
            viewType++
            if (viewType == FALLBACK_DELEGATE_VIEW_TYPE) {
                throw IllegalArgumentException(
                        "Oops, we are very close to ${Integer.MAX_VALUE}. " +
                                "It seems that there are no more free and unused view type integers left to add " +
                                "another AdapterDelegate.")
            }
        }

        return addDelegate(viewType, false, delegate)
    }

    fun addDelegate(viewType: Int, @Nonnull delegate: AdapterDelegate<T>): AdapterDelegatesManager<T> {
        return addDelegate(viewType, false, delegate)
    }

    private fun addDelegate(viewType: Int, allowReplacingDelegate: Boolean, @Nonnull delegate: AdapterDelegate<T>): AdapterDelegatesManager<T> {
        if (viewType == FALLBACK_DELEGATE_VIEW_TYPE)
            throw IllegalArgumentException("The view type = $FALLBACK_DELEGATE_VIEW_TYPE"
                    + " is reserved for fallback adapter delegate (see setFallbackDelegate()). Please use another view type.")

        if (!allowReplacingDelegate && delegates.get(viewType) != null)
            throw IllegalArgumentException(
                    "An AdapterDelegate is already registered for the viewType = $viewType. " +
                            "Already registered AdapterDelegate is ${delegates.get(viewType)}")
        delegates.put(viewType, delegate)
        return this
    }

    private fun removeDelegate(delegate: AdapterDelegate<T>): AdapterDelegatesManager<T> {
        val indexToRemove = delegates.indexOfValue(delegate)
        if (indexToRemove >= 0) delegates.removeAt(indexToRemove)
        return this
    }

    private fun removeDelegate(viewType: Int): AdapterDelegatesManager<T> {
        delegates.remove(viewType)
        return this
    }

    fun getItemViewType(item: T, position: Int): Int {
        val delegatesCount = delegates.size()
        for (i: Int in 0..delegatesCount) {
            var delegate: AdapterDelegate<T> = delegates.valueAt(i)
            if(delegate.isForViewType(item, position)) return delegates.keyAt(i)
        }

        return FALLBACK_DELEGATE_VIEW_TYPE
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var delegate = delegates.get(viewType)
        if (delegate == null) {
            delegate = fallbackDelegate
        }
        return delegate.onCreateViewHolder(parent)
    }

    fun onBindViewHolder(@Nonnull item: T, position: Int, @Nonnull viewHolder: RecyclerView.ViewHolder) {
        getDelegateForViewType(viewHolder.itemViewType).onBindViewHolder(item, position, viewHolder)
    }

    fun setFallbackDelegate(@Nonnull fallbackDelegate: AdapterDelegate<T>): AdapterDelegatesManager<T> {
        this.fallbackDelegate = fallbackDelegate
        return this
    }

    fun getViewType(@Nonnull delegate: AdapterDelegate<T>): Int {
        val index = delegates.indexOfValue(delegate)
        if(index != -1) return delegates.keyAt(index) else return -1
    }

    private fun getDelegateForViewType(viewType: Int): AdapterDelegate<T> {
        var delegate: AdapterDelegate<T> = delegates.get(viewType)!!
        if (delegate == null) {
            if (fallbackDelegate == null) {
                throw NullPointerException("No AdapterDelegate added for ViewType $viewType")
            } else {
                delegate = fallbackDelegate
            }
        }
        return delegate
    }
}