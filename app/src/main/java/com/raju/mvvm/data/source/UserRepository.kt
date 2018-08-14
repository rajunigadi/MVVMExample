package com.raju.mvvm.data.source

import android.arch.lifecycle.LiveData
import com.raju.mvvm.AppExecutors
import com.raju.mvvm.data.model.User
import com.raju.mvvm.data.model.base.Resource
import com.raju.mvvm.data.source.local.dao.UserDao
import com.raju.mvvm.data.source.remote.api.UserApi
import com.raju.mvvm.utilities.RateLimiter
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

// to do testing
//@OpenForTesting
@Singleton
class UserRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val userDao: UserDao,
        private val userApi: UserApi) {

    private val userListRateLimit = RateLimiter<String>(1, TimeUnit.MINUTES)

    fun loadUser(): LiveData<Resource<List<User>>> {
        return object : NetworkBoundResource<List<User>, List<User>>(appExecutors) {
            override fun saveCallResult(item: List<User>) {
                userDao.add(item)
            }

            override fun shouldFetch(data: List<User>?): Boolean {
                Timber.d("Data ${data}")
                return data == null || data.isEmpty() || userListRateLimit.shouldFetch("users")
            }

            override fun loadFromDb() = userDao.getUsers()

            override fun createCall() = userApi.getUsers()

            override fun onFetchFailed() {
                userListRateLimit.reset("users")
            }
        }.asLiveData()
    }
}