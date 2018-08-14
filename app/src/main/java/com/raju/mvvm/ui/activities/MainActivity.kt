package com.raju.mvvm.ui.activities

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.raju.mvvm.R
import com.raju.mvvm.data.model.User
import com.raju.mvvm.data.model.base.ListItem
import com.raju.mvvm.ui.activities.base.BaseActivity
import com.raju.mvvm.ui.adapters.UserAdapter
import com.raju.mvvm.ui.adapters.delegates.base.ClickableItemTarget
import com.raju.mvvm.ui.adapters.delegates.base.ItemClickListener
import com.raju.mvvm.ui.viewmodel.UserViewModel
import com.raju.mvvm.ui.viewmodel.base.GitHubViewModelFactory
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity(), ItemClickListener<User> {

    @BindView(R.id.recycler_view)
    lateinit protected var recyclerView: RecyclerView

    @Inject
    lateinit var userViewModel: UserViewModel

    @Inject
    lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setupRecyclerView()
        setupAdapter(recyclerView)

        userViewModel.loadUsers()

        userViewModel.userResult().observe(this, Observer<MutableList<User>> {
            Timber.d("Matches onChange() ${it?.size}")
            var list:MutableList<ListItem> = mutableListOf()
            list.addAll(it!!)
            adapter.refactorItems(list!!)
        })

        userViewModel.userError().observe(this, Observer<String> {
            Timber.d(it)
        })
    }

    override fun onItemClick(view: View, position: Int, item: User) {

    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    private fun setupAdapter(recyclerView: RecyclerView) {
        if (recyclerView.adapter !is UserAdapter) {
            recyclerView.adapter = adapter

            val target = adapter as ClickableItemTarget<User>
            target.setOnItemClickListener(this)

            adapter.setup()
        }
    }
}
