package com.jukti.clearscoredemo.di

import com.jukti.clearscoredemo.data.remote.ClearScoreDataSource
import com.jukti.clearscoredemo.data.repository.ClearScoreRepositoryImp
import com.jukti.clearscoredemo.domain.ClearScoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideClearScoreRepository(dataSource: ClearScoreDataSource) : ClearScoreRepository {
        return ClearScoreRepositoryImp(dataSource)
    }
}