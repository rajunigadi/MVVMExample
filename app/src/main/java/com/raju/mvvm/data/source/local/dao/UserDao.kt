package com.raju.mvvm.data.source.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.raju.mvvm.data.model.User
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * Created by Rajashekhar Vanahalli on 16/07/18.
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(users: List<User>)

    @Query("SELECT * FROM user")
    fun getUsers(): MutableList<User>

    @Query("SELECT * FROM user WHERE name LIKE :text")
    fun searchUsers(text: String): MutableList<User>
}