package com.teamfillin.fillin.data.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRefresh(
    val status: Int,
    val success: Boolean,
    val message: String,
    val tokens: AuthToken
) {
    @Serializable
    data class AuthToken(
        val newAccessToken: String,
        @SerialName("refreshtoken")
        val refreshToken: String
    )
}
