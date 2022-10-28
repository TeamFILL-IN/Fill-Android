package com.teamfillin.fillin.data.response

import com.teamfillin.fillin.domain.entity.User
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserInfo(
    val user: UserInfo
) {
    @Serializable
    data class UserInfo(
        val createdAt: String,
        val email: String?,
        val id: Int,
        val idKey: String,
        val imageUrl: String,
        val isDeleted: Boolean,
        val nickname: String,
        val refreshToken: String,
        val social: String,
        val updatedAt: String,
        val camera: String?
    ) {
        fun toUser(): User {
            return User(createdAt, email, id, idKey, imageUrl, isDeleted, nickname, refreshToken, social, updatedAt, camera)
        }
    }
}