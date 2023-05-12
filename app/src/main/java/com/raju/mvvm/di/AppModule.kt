package com.raju.mvvm.di

import com.raju.mvvm.utils.DefaultDispatcherProvider
import com.raju.mvvm.utils.DispatcherProvider
import com.raju.mvvm.utils.END_POINT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(BODY)
        return logging
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(END_POINT)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
        return retrofit.build()
    }

    @Provides
    @Singleton
    fun providesDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }
}