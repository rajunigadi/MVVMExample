package com.raju.mvvm.dagger.modules.base

import com.raju.mvvm.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Named

/**
 * Created by Rajashekhar Vanahalli on 14/08/18.
 */
@Module
class HttpModule {

    @Provides
    @Named("LOGGING")
    internal fun provideLoggingInterceptor(): Interceptor {
        //For Debug
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.d(message) })
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.HEADERS else HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }
}