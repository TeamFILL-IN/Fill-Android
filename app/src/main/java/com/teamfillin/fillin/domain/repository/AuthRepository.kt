package com.teamfillin.fillin.domain.repository

import com.teamfillin.fillin.domain.entity.User

interface AuthRepository {
    suspend fun login(token: String, id: String)
    suspend fun getUser(): User
}