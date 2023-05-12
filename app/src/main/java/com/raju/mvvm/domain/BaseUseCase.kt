package com.raju.mvvm.domain

import com.raju.mvvm.data.User
import kotlinx.coroutines.flow.Flow

interface BaseUseCase {
    suspend fun login(username: String, password: String): Flow<User>
    suspend fun getUsers(): Flow<List<User>>
}