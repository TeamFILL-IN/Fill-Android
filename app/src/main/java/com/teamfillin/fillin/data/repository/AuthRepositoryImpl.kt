package com.teamfillin.fillin.data.repository

import com.teamfillin.fillin.data.datasource.AuthDataSource
import com.teamfillin.fillin.data.local.FillInDataStore
import com.teamfillin.fillin.data.response.toEntity
import com.teamfillin.fillin.data.service.AuthService
import com.teamfillin.fillin.domain.entity.Auth
import com.teamfillin.fillin.domain.entity.User
import com.teamfillin.fillin.domain.repository.AuthRepository
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: FillInDataStore,
    private val service: AuthService,
    private val dataSource: AuthDataSource
) : AuthRepository {
    override suspend fun login(token: String, id: String) {
        runCatching {
            service.login(token, id = id).await()
        }.fold({
            val auth = it.data.toEntity()
            with(dataStore) {
                userToken = auth.accessToken
                refreshToken = auth.refreshToken
            }
            if (auth is Auth.SignUp) dataStore.nickname = auth.nickName
        }, Timber::e)
    }

    override suspend fun getUser(): User {
        return dataSource.getUser().toUser()
    }
}
