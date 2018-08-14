package com.raju.mvvm.ui.activities

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.raju.mvvm.R
import com.raju.mvvm.data.model.User
import com.raju.mvvm.data.model.base.ListItem
import com.raju.mvvm.data.source.local.dao.UserDao
import com.raju.mvvm.ui.activities.base.BaseActivity
import com.raju.mvvm.ui.adapters.UserAdapter
import com.raju.mvvm.ui.adapters.delegates.base.ClickableItemTarget
import com.raju.mvvm.ui.adapters.delegates.base.ItemClickListener
import com.raju.mvvm.ui.custom.RxSearchObservable
import com.raju.mvvm.ui.viewmodel.UserViewModel
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem


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
            var list:MutableList<ListItem> = mutableListOf()
            list.addAll(it!!)
            adapter.refactorItems(list!!)
        })

        userViewModel.userError().observe(this, Observer<String> {
            Timber.d(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == android.R.id.home) {
            finish()
        } else if (id == R.id.action_search) {
            val searchView = item.getActionView() as SearchView
            setUpSearchObservable(searchView)
        }

        return super.onOptionsItemSelected(item)
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


    private fun setUpSearchObservable(searchView: SearchView) {
        RxSearchObservable.fromView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(Predicate<String> { text -> if (text.isEmpty()) false else true })
                .distinctUntilChanged()
                .switchMap(Function<String, ObservableSource<MutableList<User>>> { query ->
                    if (query != null) {
                        searchFromNetwork(query)
                    } else {
                        Observable.empty()
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<List<User>> {
                    it -> Timber.d("Result: $it")
                    var list:MutableList<ListItem> = mutableListOf()
                    list.addAll(it!!)
                    adapter.refactorItems(list!!)
                })
    }

    private fun searchFromNetwork(query: String): Observable<MutableList<User>> {
        var str = StringBuilder("%")
        str.append(query)
        str.append("%")
        Timber.d("Search: ${str.toString()}")
        return userViewModel.searchUsers(str.toString()).subscribeOn(Schedulers.io())
    }
}
