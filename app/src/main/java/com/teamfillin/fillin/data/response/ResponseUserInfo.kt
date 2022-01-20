package com.teamfillin.fillin.data.response

data class ResponseUserInfo(
    val user: User
) {
    data class User(
        val createdAt: String,
        val email: Any,
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