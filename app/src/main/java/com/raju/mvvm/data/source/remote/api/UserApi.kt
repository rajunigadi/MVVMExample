package com.raju.mvvm.data.source.remote.api

import android.arch.lifecycle.LiveData
import com.raju.mvvm.data.model.User
import com.raju.mvvm.data.source.remote.api.base.ApiResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Rajashekhar Vanahalli on 16/07/18.
 */
interface UserApi {
    @GET("/users")
    fun getUsers(): LiveData<ApiResponse<List<User>>>
}