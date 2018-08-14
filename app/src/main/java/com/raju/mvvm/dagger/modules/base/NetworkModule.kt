package com.raju.mvvm.dagger.modules.base

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.raju.mvvm.data.source.remote.http.HttpClientBuilder
import com.raju.mvvm.utilities.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Rajashekhar Vanahalli on 14/08/18.
 */
@Module(includes = arrayOf(HttpModule::class))
class NetworkModule {

    @Provides
    @Singleton
    internal fun providesOkHttpClient(context: Context, @Named("LOGGING") loggingInterceptor: Interceptor): OkHttpClient {
        val builder = HttpClientBuilder(context)
                .setTimeouts(30)
                .setCache("MVVMApp", 20)
                .setLoggingInterceptor(loggingInterceptor)

        return builder.build()
    }

    @Provides
    @Singleton
    internal fun providesRetrofit(client: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit
    }
}