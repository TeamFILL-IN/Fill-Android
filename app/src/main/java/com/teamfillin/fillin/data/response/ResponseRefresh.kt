package com.teamfillin.fillin.data.response

import com.google.gson.annotations.SerializedName

data class ResponseRefresh(
    val status: Int,
    val success: Boolean,
    val message: String,
    val tokens: AuthToken
) {
    data class AuthToken(
        val newAccessToken: String,
        @SerializedName("refreshtoken")
        val refreshToken: String
    )
}
