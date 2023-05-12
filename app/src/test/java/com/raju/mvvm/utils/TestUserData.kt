package com.raju.mvvm.utils

import com.raju.mvvm.data.User

object TestUserData {
    // Set up the test data
    const val username = "testuser"
    const val password = "password"

    val user = User("John")

    val users = listOf<User>(User("John"), User("Joe"))
}