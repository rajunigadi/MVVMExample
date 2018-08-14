package com.raju.mvvm.ui.adapters

import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.raju.mvvm.AppExecutors
import com.raju.mvvm.R
import com.raju.mvvm.data.model.User
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import com.raju.mvvm.databinding.LayoutUserItemBinding
import com.raju.mvvm.ui.adapters.base.DataBoundListAdapter

/**
 * A RecyclerView adapter for [Repo] class.
 */
class UserAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val repoClickCallback: ((User) -> Unit)?
) : DataBoundListAdapter<User, LayoutUserItemBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.name == newItem.name
            }
        }
) {

    override fun createBinding(parent: ViewGroup): LayoutUserItemBinding {
        val binding = DataBindingUtil.inflate<LayoutUserItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.layout_user_item,
                parent,
                false,
                dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.user?.let {
                repoClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: LayoutUserItemBinding, item: User) {
        binding.user = item
    }
}
