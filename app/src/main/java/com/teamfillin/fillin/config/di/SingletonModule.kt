package com.teamfillin.fillin.config.di

import android.app.Application
import android.content.Context
import com.teamfillin.fillin.data.local.FillInDataStore
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
    @ApplicationContext
    fun provideApplication(application: Application) = application

    @Provides
    @Singleton
    fun provideResolutionMetrics(@ApplicationContext context: Application) =
        ResolutionMetrics(context)

    @Provides
    @Singleton
    fun provideFillInDataStore(@ApplicationContext context: Context) = FillInDataStore(context)
}