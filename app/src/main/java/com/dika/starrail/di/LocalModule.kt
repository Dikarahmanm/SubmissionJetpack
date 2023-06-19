package com.dika.starrail.di

import android.app.Application
import androidx.room.Room
import com.dika.starrail.data.local.CharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application) = Room
        .databaseBuilder(application, CharacterDatabase::class.java, "starrail.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideDao(database: CharacterDatabase) = database.StarRailDao()
}