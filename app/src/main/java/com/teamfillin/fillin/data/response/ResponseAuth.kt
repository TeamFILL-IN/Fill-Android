package com.teamfillin.fillin.data.response

sealed class ResponseAuth(
    open val email: String?,
    open val accessToken: String,
    open val refreshToken: String
) {
    data class Login(
        override val email: String?,
        override val accessToken: String,
        override val refreshToken: String
    ) : ResponseAuth(email, accessToken, refreshToken)

    data class SignUp(
        override val email: String?,
        val nickName: String,
        override val accessToken: String,
        override val refreshToken: String
    ) : ResponseAuth(email, accessToken, refreshToken)
}
