package com.raju.mvvm.ui.adapters.delegates.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.raju.mvvm.data.model.base.ListItem
import javax.annotation.Nonnull

abstract class ListAdapterDelegate<T: ListItem>
constructor(private val layoutId: Int, private val recordType: Class<T>): AdapterDelegate<MutableList<T>> {

    private var delegateClickListener: DelegateClickListener? = null

    @Nonnull
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return formViewHolder(view)
    }

    override fun onBindViewHolder(items: MutableList<T>, position: Int, holder: RecyclerView.ViewHolder) {
        val viewModel: T = items.get(position)
        (holder as ViewAdapterHolder<T>).setData(position, viewModel)
    }

    override fun isForViewType(items: MutableList<T>, position: Int): Boolean {
        var item: ListItem = items.get(position)
        return recordType.isInstance(item)
    }

    override fun setDelegateClickListener(delegateClickListener: DelegateClickListener) {
        this.delegateClickListener = delegateClickListener
    }

    protected abstract fun formViewHolder(view: View): RecyclerView.ViewHolder

    protected open inner class AdapterViewHolder protected constructor(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            ButterKnife.bind(this, view)
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            delegateClickListener?.onClick(layoutPosition, view)
        }
    }
}