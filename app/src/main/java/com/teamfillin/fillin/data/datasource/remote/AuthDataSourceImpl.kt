package com.teamfillin.fillin.data.datasource.remote

import com.teamfillin.fillin.data.datasource.AuthDataSource
import com.teamfillin.fillin.data.response.ResponseUserInfo
import com.teamfillin.fillin.data.service.UserService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val service: UserService
): AuthDataSource {
    override suspend fun getUser(): ResponseUserInfo.UserInfo {
        return service.getUserInfo().data.user
    }
}