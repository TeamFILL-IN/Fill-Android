package com.teamfillin.fillin.config.di

import com.teamfillin.fillin.data.datasource.AuthDataSource
import com.teamfillin.fillin.data.datasource.MapDataSource
import com.teamfillin.fillin.data.datasource.PhotoDataSource
import com.teamfillin.fillin.data.datasource.remote.AuthDataSourceImpl
import com.teamfillin.fillin.data.datasource.remote.MapDataSourceImpl
import com.teamfillin.fillin.data.datasource.remote.PhotoDataSourceImpl
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

    @Provides
    @Singleton
    fun providePhotoDataSource(photoDataSource: PhotoDataSourceImpl): PhotoDataSource = photoDataSource

    @Provides
    @Singleton
    fun provideAuthDataSource(authDataSource: AuthDataSourceImpl): AuthDataSource = authDataSource
}