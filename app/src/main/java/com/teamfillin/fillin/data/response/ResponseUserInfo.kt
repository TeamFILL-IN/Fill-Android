package com.teamfillin.fillin.data.response

data class ResponseUserInfo(
    val user: User,
) {
    data class User(
        val email: String,
        val id: Int,
        val imageUrl: String,
        val isDeleted: Boolean,
        val nickname: String,
        val refreshToken: String,
        val social: String,
        val updatedAt: String
    )
}
