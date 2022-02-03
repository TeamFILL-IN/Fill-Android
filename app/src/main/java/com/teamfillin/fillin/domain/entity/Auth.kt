package com.teamfillin.fillin.domain.entity

sealed class Auth(
    open val email: String?,
    open val accessToken: String,
    open val refreshToken: String
) {
    data class Login(
        override val email: String?,
        override val accessToken: String,
        override val refreshToken: String
    ) : Auth(email, accessToken, refreshToken)

    data class SignUp(
        override val email: String?,
        val nickName: String,
        override val accessToken: String,
        override val refreshToken: String
    ) : Auth(email, accessToken, refreshToken)
}