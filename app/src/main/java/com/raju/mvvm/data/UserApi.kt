package com.raju.mvvm.data

import retrofit2.http.GET

interface UserApi {
    @GET("/users")
    suspend fun getUsers(): List<User>
}