package com.teamfillin.fillin.config.di

import com.teamfillin.fillin.data.service.AuthService
import com.teamfillin.fillin.data.service.NewPhotoService
import com.teamfillin.fillin.data.service.StudioService
import com.teamfillin.fillin.data.service.experimental.PagingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideStudioService(retrofit: Retrofit): StudioService =
        retrofit.create(StudioService::class.java)

    @Provides
    @Singleton
    fun provideNewPhotoService(retrofit: Retrofit): NewPhotoService =
        retrofit.create(NewPhotoService::class.java)

    @Provides
    @Singleton
    fun providePagingService(retrofit: Retrofit): PagingService =
        retrofit.create(PagingService::class.java)
}