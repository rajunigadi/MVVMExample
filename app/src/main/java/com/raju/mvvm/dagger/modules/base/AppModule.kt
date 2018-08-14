package com.raju.mvvm.dagger.modules.base

import android.app.Application
import android.content.Context
import com.raju.mvvm.BuildConfig
import com.raju.mvvm.MVVMApp
import com.raju.mvvm.data.source.remote.api.UserApi
import com.raju.mvvm.data.source.remote.http.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Rajashekhar Vanahalli on 14/08/18.
 */
@Module
class AppModule {

    @Provides
    internal fun provideContext(application: MVVMApp): Context {
        return application.getApplicationContext()
    }

    @Provides
    internal fun provideApplication(application: MVVMApp): Application {
        return application
    }

    @Singleton
    @Provides
    fun provideGithubService(): UserApi {

        //For Debug
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.d(message) })
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.HEADERS else HttpLoggingInterceptor.Level.NONE

        val okClientBuilder = OkHttpClient.Builder()
        okClientBuilder.addInterceptor(httpLoggingInterceptor!!)

        return Retrofit.Builder()
                .client(okClientBuilder.build())
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(UserApi::class.java)
    }
}