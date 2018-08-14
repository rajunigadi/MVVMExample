package com.raju.mvvm.data.source.remote.api

import com.raju.mvvm.data.model.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Rajashekhar Vanahalli on 16/07/18.
 */
interface UserApi {
    @GET("/users")
    fun getUsers(): Observable<MutableList<User>>
}