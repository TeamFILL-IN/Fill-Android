package com.teamfillin.fillin.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserInfo(
    val user: User
) {
    @Serializable
    data class User(
        val createdAt: String,
        val email: String?,
        val id: Int,
        val idKey: String,
        val imageUrl: String,
        val isDeleted: Boolean,
        val nickname: String,
        val refreshToken: String,
        val social: String,
        val updatedAt: String
    )
}