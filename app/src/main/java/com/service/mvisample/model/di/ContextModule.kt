package com.service.mvisample.model.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContextModule(private val application: Application) {
    @Singleton
    @Provides
    fun context(): Context = application
}
