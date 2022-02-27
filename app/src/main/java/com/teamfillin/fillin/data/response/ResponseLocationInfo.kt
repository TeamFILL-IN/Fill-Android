package com.teamfillin.fillin.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseLocationInfo(
    val name: String,
    val location: String
)