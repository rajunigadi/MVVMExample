package com.raju.mvvm.ui.adapters

import com.raju.mvvm.data.model.base.ListItem
import com.raju.mvvm.ui.adapters.delegates.UserDelegate
import com.raju.mvvm.ui.adapters.delegates.base.AdapterDelegate
import com.raju.mvvm.ui.adapters.delegates.base.DelegatingListAdapter
import com.raju.mvvm.ui.adapters.delegates.base.ListAdapterDelegate
import javax.inject.Inject

class UserAdapter @Inject internal constructor() : DelegatingListAdapter<ListItem>() {

    override fun createDelegates(): Array<AdapterDelegate<MutableList<ListItem>>> {
        val delegate: ListAdapterDelegate<ListItem> = clickable(UserDelegate() as ListAdapterDelegate<ListItem>)
        return arrayOf(delegate)
    }
}