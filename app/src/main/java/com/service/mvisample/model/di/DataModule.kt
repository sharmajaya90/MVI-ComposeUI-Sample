package com.service.mvisample.model.di

import android.content.Context
import androidx.room.Room
import com.service.mvisample.model.repository.database.AppDatabase
import com.service.mvisample.model.repository.database.dao.UserInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "techit_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    @Provides
    @Singleton
    fun provideUserInfoDao(database: AppDatabase): UserInfoDao {
        return database.provideUserInfoDao()
    }

}