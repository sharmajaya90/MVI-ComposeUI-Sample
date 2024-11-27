package com.service.mvisample.model.di

import com.google.gson.GsonBuilder
import com.service.mvisample.commonutils.extensions.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object  NetworkModule {
    private var CONNECT_TIMEOUT: Long = 3 * 60L //establish connection with the server upto 30 seconds, by default 10 seconds.
    private var READ_TIMEOUT: Long = 3 * 60L
    private var WRITE_TIMEOUT: Long = 3 * 60L


    @Provides
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .cache(null)
            .build()
    }

    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://6746cebb38c8741641d43b7f.mockapi.io/service/")
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .build()
    }

}