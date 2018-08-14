package com.raju.mvvm.ui.adapters.delegates.base

import android.view.View

interface DelegateClickListener {
    fun onClick(position: Int, v: View)
}