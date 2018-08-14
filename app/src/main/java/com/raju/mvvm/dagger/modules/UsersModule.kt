package com.raju.mvvm.dagger.modules

import com.raju.mvvm.data.source.remote.api.UserApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Rajashekhar Vanahalli on 16/07/18.
 */
@Module
class UsersModule {

    @Provides
    fun providesUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}