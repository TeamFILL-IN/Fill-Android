package com.teamfillin.fillin.data.response

data class ResponseStudioLocation(
    val studios: List<StudioLocation>
) {
    data class StudioLocation(
        val id: Int,
        val lati: Double,
        val long: Double
    )
}