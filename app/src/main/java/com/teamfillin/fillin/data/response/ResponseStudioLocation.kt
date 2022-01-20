package com.teamfillin.fillin.data.response

data class ResponseStudioLocation(
    val studios: List<Studio>,
) {
    data class Studio(
        val id: Int,
        val lati: Double,
        val long: Double
    )
}