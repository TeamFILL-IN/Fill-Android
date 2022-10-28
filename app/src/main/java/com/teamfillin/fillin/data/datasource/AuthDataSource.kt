package com.teamfillin.fillin.data.datasource

import com.teamfillin.fillin.data.response.ResponseUser

interface AuthDataSource {
    suspend fun getUser(): ResponseUser.UserResponse
}