package com.teamfillin.fillin.config.di

import com.teamfillin.fillin.data.repository.AuthRepositoryImpl
import com.teamfillin.fillin.data.repository.MapSearchRepositoryImpl
import com.teamfillin.fillin.data.repository.PhotoPagingRepository
import com.teamfillin.fillin.data.service.PagingService
import com.teamfillin.fillin.domain.repository.AuthRepository
import com.teamfillin.fillin.domain.repository.MapSearchRepository
import com.teamfillin.fillin.presentation.filmroll.add.repository.AddPhotoRepository
import com.teamfillin.fillin.presentation.filmroll.add.repository.AddPhotoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository = authRepository

    @Provides
    @Singleton
    fun provideAddPhotoRepository(repository: AddPhotoRepositoryImpl): AddPhotoRepository =
        repository

    @Provides
    @Singleton
    fun providePhotoPagingRepository(service: PagingService): PhotoPagingRepository =
        PhotoPagingRepository(service)

    @Provides
    @Singleton
    fun provideMapSearchRepository(mapSearchRepository: MapSearchRepositoryImpl) : MapSearchRepository =
        mapSearchRepository
}