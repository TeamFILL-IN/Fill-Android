package com.teamfillin.fillin.data.response

data class ResponseFilmStyle(
    val films: List<FIlmStyleInfo>
) {
    data class FIlmStyleInfo(
        val id: Int,
        val name: String,
        val styleId: Int
    )
}
