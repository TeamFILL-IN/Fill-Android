package com.teamfillin.fillin.data.repository

import com.teamfillin.fillin.data.local.FillInDataStore
import com.teamfillin.fillin.data.response.ResponseAuth
import com.teamfillin.fillin.data.service.AuthService
import com.teamfillin.fillin.domain.repository.AuthRepository
import retrofit2.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: FillInDataStore,
    private val service: AuthService
) : AuthRepository {
    override suspend fun login(token: String): Result<Boolean> {
        runCatching {
            service.login(hashMapOf("token" to token, "social" to "kakao")).await()
        }.fold({
            with(dataStore) {
                userToken = it.data.accessToken
                refreshToken = it.data.refreshToken
            }
            if (it.data is ResponseAuth.SignUp) dataStore.nickname = it.data.nickName
            return Result.success(true)
        }, {
            return Result.failure(it)
        })
    }
}