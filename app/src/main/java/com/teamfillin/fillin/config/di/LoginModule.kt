package com.teamfillin.fillin.config.di

import android.content.Context
import com.kakao.sdk.user.UserApiClient
import com.teamfillin.fillin.presentation.login.KakaoAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object LoginModule {
    @Provides
    @ActivityScoped
    fun provideUserApiClient(): UserApiClient = UserApiClient.instance

    @Provides
    fun provideKakaoAuthService(
        @ActivityContext context: Context,
        client: UserApiClient
    ) = KakaoAuthService(context, client)
}