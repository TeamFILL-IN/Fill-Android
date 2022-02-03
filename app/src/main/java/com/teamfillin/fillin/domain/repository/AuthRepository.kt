package com.teamfillin.fillin.domain.repository

interface AuthRepository {
    suspend fun login(token: String, id: String)
}