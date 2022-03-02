package com.teamfillin.fillin.config.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.teamfillin.fillin.FlipperInitializer
import com.teamfillin.fillin.core.converter.JsonConverterFactory
import com.teamfillin.fillin.core.converter.KotlinSerializer
import com.teamfillin.fillin.data.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor = authInterceptor

    @Provides
    @Singleton
    fun provideOkHttpInterceptor(
        authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(authInterceptor)
            .apply { FlipperInitializer.addFlipperNetworkPlguin(this).build() }
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = false
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    @KotlinSerializer
    fun provideKotlinSerializer(json: Json): Converter.Factory =
        json.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun provideJsonConverter(
        gsonConverterFactory: GsonConverterFactory,
        @KotlinSerializer kotlinSerializationConverter: Converter.Factory
    ): JsonConverterFactory =
        JsonConverterFactory(gsonConverterFactory, kotlinSerializationConverter)

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        jsonConverterFactory: JsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(jsonConverterFactory)
        .build()

    const val BASE_URL = "https://asia-northeast3-fill-in-13efb.cloudfunctions.net/app/api/"
}