package com.teamfillin.fillin.data.response

import com.teamfillin.fillin.domain.entity.Auth

data class ResponseAuth(
    val email: String?,
    val nickName: String? = null,
    val accessToken: String,
    val refreshToken: String
)

fun ResponseAuth.toEntity(): Auth = when (nickName) {
    null -> Auth.Login(email, accessToken, refreshToken)
    else -> Auth.SignUp(email, nickName, accessToken, refreshToken)
}
