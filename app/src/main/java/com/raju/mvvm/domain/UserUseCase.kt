package com.raju.mvvm.domain

import com.raju.mvvm.data.User
import com.raju.mvvm.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserUseCase(
    private val repository: UserRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseUseCase {
    override suspend fun login(username: String, password: String): Flow<User> {
        return flow { emit(repository.login(username, password)) }.flowOn(dispatcherProvider.io)
    }
    override suspend fun getUsers(): Flow<List<User>> {
        return flow { emit(repository.getUsers()) }.flowOn(dispatcherProvider.io)
    }
}