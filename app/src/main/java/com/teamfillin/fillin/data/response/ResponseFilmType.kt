package com.teamfillin.fillin.data.response

data class ResponseFilmType(
    val films: List<Films>
) {
    data class Films(
        val id: Int,
        val name: String,
        val styleId: Int
    )
}
