package com.dika.starrail.di

import com.dika.starrail.data.local.StarRailDao
import com.dika.starrail.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideRepository(StarRailDao: StarRailDao) = Repository(StarRailDao)
}