package com.teamfillin.fillin.config.di

import android.app.Application
import android.content.Context
import com.teamfillin.fillin.design.ResolutionMetrics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {
    @Provides
    @Singleton
    fun provideResolutionMetrics(@ApplicationContext context: Context) =
        ResolutionMetrics(context as Application)
}