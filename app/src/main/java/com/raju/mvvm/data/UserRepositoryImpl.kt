package com.raju.mvvm.data

import com.raju.mvvm.domain.UserRepository
import kotlinx.coroutines.delay

class UserRepositoryImpl(private val userApi: UserApi) : UserRepository {
    override suspend fun login(username: String, password: String): User {
        delay(1000)
        return User("John")
    }

    override suspend fun getUsers(): List<User> = userApi.getUsers()
}