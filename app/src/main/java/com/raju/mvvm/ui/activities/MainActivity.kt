package com.raju.mvvm.ui.activities

import android.os.Bundle

import butterknife.ButterKnife
import com.raju.mvvm.R

import android.support.v7.widget.SearchView
import android.view.*
import com.raju.mvvm.ui.activities.base.BaseActivity
import com.raju.mvvm.ui.fragments.UserFragment


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.disallowAddToBackStack()

        val messageFragment = UserFragment()
        fragmentTransaction.replace(R.id.container, messageFragment)
        fragmentTransaction.commit()
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
            // setUpSearchObservable(searchView)
        }

        return super.onOptionsItemSelected(item)
    }
}
