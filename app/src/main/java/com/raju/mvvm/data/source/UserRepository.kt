package com.raju.mvvm.data.source

import com.raju.mvvm.data.model.User
import com.raju.mvvm.data.source.local.dao.UserDao
import com.raju.mvvm.data.source.remote.api.UserApi
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi, private val userDao: UserDao) {

    fun getUsers(): Observable<MutableList<User>>? {
        val observableApi = getUsersFromApi()
        val observableLocal = getUsersFromLocal()
        val observable = Observable.concatArrayEager(observableLocal, observableApi)
        return observable!!
    }

    private fun getUsersFromApi(): Observable<MutableList<User>>? {
        return userApi.getUsers()
                .doOnNext {
                    t: MutableList<User> ->
                    for (item in t) {
                        userDao.add(item)
                    }
                }
    }

    private fun getUsersFromLocal(): Observable<MutableList<User>>? {
        return Observable.just(userDao.getUsers()).doOnNext { Timber.d("Size: ${it.size}") }
    }

    fun searchUsers(text: String): Observable<MutableList<User>>? {
        return Observable.just(userDao.searchUsers(text)).doOnNext { Timber.d("Size: ${it.size}") }
    }
}