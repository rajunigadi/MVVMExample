package com.raju.mvvm.data.source.remote.http

import android.content.Context
import okhttp3.*
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

class HttpClientBuilder constructor(private val context: Context) {

    fun build(): OkHttpClient {
        val okClientBuilder = OkHttpClient.Builder()

        if (null != loggingInterceptor) {
            okClientBuilder.addInterceptor(loggingInterceptor!!)
        }

        val baseDir = context.cacheDir
        if (baseDir != null) {
            val cacheDir = File(baseDir, cacheName!!)
            val cacheSize = cacheSizeMB * 1024 * 1024
            okClientBuilder.cache(Cache(cacheDir, cacheSize.toLong()))
        }

        okClientBuilder.connectTimeout(this.timeoutConnect.toLong(), TimeUnit.SECONDS)
        okClientBuilder.readTimeout(this.timeoutRead.toLong(), TimeUnit.SECONDS)
        okClientBuilder.writeTimeout(this.timeoutWrite.toLong(), TimeUnit.SECONDS)

        if (null != networkInterceptor) {
            okClientBuilder.addNetworkInterceptor(networkInterceptor!!)
        }
        if (null != wireFormatHeaderInterceptor) {
            okClientBuilder.addInterceptor(wireFormatHeaderInterceptor!!)
        }

        okClientBuilder.cache(provideCache())
        return okClientBuilder.build()
    }

    fun setCache(cacheName: String, cacheSizeMB: Int): HttpClientBuilder {
        this.cacheName = cacheName
        this.cacheSizeMB = cacheSizeMB
        return this
    }

    fun setTimeouts(timeout: Int): HttpClientBuilder {
        return setConnectTimeout(timeout)
                .setReadTimeout(timeout)
                .setWriteTimeout(timeout)
    }

    fun setConnectTimeout(timeout: Int): HttpClientBuilder {
        this.timeoutConnect = timeout
        return this
    }

    fun setReadTimeout(timeout: Int): HttpClientBuilder {
        this.timeoutRead = timeout
        return this
    }

    fun setWriteTimeout(timeout: Int): HttpClientBuilder {
        this.timeoutWrite = timeout
        return this
    }

    fun setNetworkInterceptor(interceptor: Interceptor): HttpClientBuilder {
        this.networkInterceptor = interceptor
        return this
    }

    fun setWireFormatHeaderInterceptor(wireFormatHeaderInterceptor: Interceptor): HttpClientBuilder {
        this.wireFormatHeaderInterceptor = wireFormatHeaderInterceptor
        return this
    }

    fun setLoggingInterceptor(interceptor: Interceptor): HttpClientBuilder {
        this.loggingInterceptor = interceptor
        return this
    }

    private fun provideCache(): Cache? {
        var cache: Cache? = null
        try {
            cache = Cache(File(context.cacheDir, "http-cache"),
                    (10 * 1024 * 1024).toLong()) // 10 MB
        } catch (e: Exception) {
            Timber.d("Could not create Cache!")
        }

        return cache
    }

    private var timeoutConnect: Int = 0
    private var timeoutRead: Int = 0
    private var timeoutWrite: Int = 0

    private var cacheName: String? = null
    private var cacheSizeMB: Int = 0

    private var networkInterceptor: Interceptor? = null
    private var loggingInterceptor:Interceptor? = null
    private var wireFormatHeaderInterceptor:Interceptor? = null
}