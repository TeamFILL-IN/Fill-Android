package com.teamfillin.fillin.config.di

import com.teamfillin.fillin.data.datasource.MapDataSource
import com.teamfillin.fillin.data.datasource.remote.MapDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideMapDataSource(mapDataSource: MapDataSourceImpl): MapDataSource = mapDataSource
}