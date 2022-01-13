package com.teamfillin.fillin.data.repository

import com.teamfillin.fillin.data.local.FillInDataStore
import com.teamfillin.fillin.data.response.ResponseAuth
import com.teamfillin.fillin.data.response.toEntity
import com.teamfillin.fillin.data.service.AuthService
import com.teamfillin.fillin.domain.entity.Auth
import com.teamfillin.fillin.domain.repository.AuthRepository
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: FillInDataStore,
    private val service: AuthService
) : AuthRepository {
    override suspend fun login(token: String) {
        runCatching {
            service.login(token).await()
        }.fold({
            val auth = it.data.toEntity()
            with(dataStore) {
                userToken = auth.accessToken
                refreshToken = auth.refreshToken
            }
            if (auth is Auth.SignUp) dataStore.nickname = auth.nickName
        }, Timber::e)
    }
}
