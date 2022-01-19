package com.teamfillin.fillin.config.di

import com.teamfillin.fillin.data.service.*
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
    fun provideFilmPhotoService(retrofit: Retrofit): FilmPhotoService =
        retrofit.create(FilmPhotoService::class.java)

    @Provides
    @Singleton
    fun provideFilmStyleService(retrofit: Retrofit): FilmStyleService =
        retrofit.create(FilmStyleService::class.java)
}