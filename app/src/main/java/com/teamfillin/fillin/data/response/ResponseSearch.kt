package com.teamfillin.fillin.data.response

data class ResponseSearch(
    val studios: List<Studio>
) {
    data class Studio(
        val id: Int,
        val name: String,
        val address: String
    )
}
