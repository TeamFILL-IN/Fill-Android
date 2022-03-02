package com.teamfillin.fillin.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseFilmType(
    val films: List<Films>
) {
    @Serializable
    data class Films(
        val id: Int,
        val name: String,
        val styleId: Int
    )
}
