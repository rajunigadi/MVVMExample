package com.raju.mvvm.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.raju.mvvm.data.model.User
import com.raju.mvvm.data.source.local.dao.UserDao

/**
 * Created by Rajashekhar Vanahalli on 16/07/18.
 */
@Database(entities = arrayOf(User::class), version = 1)
abstract class GitHubDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}