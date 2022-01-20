package com.teamfillin.fillin.data.response

data class ResponseSearch(
    val studios: List<StudioResponse>
) {
    data class StudioResponse(
        val id: Int,
        val name: String,
        val address: String
    )
}
