package com.raju.mvvm.ui.adapters.delegates.base

import android.view.View
import com.raju.mvvm.data.model.base.ListItem

/**
 * Created by Rajashekhar Vanahalli on 23/07/18.
 */
abstract class DelegatingListAdapter <T:ListItem> : DelegatingAdapter<MutableList<T>>(), ClickableItemTarget<T> {

    override var data: MutableList<T>? = mutableListOf()
    override var filteredData: MutableList<T>? = mutableListOf()

    fun addItem(item: T) {
        data?.add(item)
        filteredData?.add(item)
        notifyItemInserted(itemCount)
    }

    fun addItems(items: MutableList<T>) {
        val size: Int = items.size
        data?.addAll(items)
        filteredData?.addAll(items)
        notifyItemRangeInserted(itemCount, size)
    }

    fun refactorItems(items: MutableList<T>) {
        data?.clear()
        data?.addAll(items)

        filteredData?.clear()
        filteredData?.addAll(items)

        notifyDataSetChanged()
    }

    fun removeItems() {
        data?.clear()
        filteredData?.clear()
        notifyDataSetChanged()
    }

    fun getLastItem(): T? {
        return filteredData!!.get(filteredData!!.size - 1)
    }

    override fun onClick(position: Int, v: View) {
        if (filteredData != null && filteredData!!.size > position)
            listener?.onItemClick(v, position, filteredData!!.get(position))
    }

    override fun getItemCount(): Int {
        return filteredData!!.size
    }

    override fun setOnItemClickListener(listener: ItemClickListener<T>) {
        this.listener = listener
    }

    fun getItemList(): MutableList<T> {
        return filteredData!!
    }

    protected var listener: ItemClickListener<T>? = null
}