package com.raju.mvvm.domain

import com.raju.mvvm.data.User

interface UserRepository {
    suspend fun login(username: String, password: String): User
    suspend fun getUsers(): List<User>
}