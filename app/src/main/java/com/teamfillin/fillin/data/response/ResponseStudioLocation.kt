package com.teamfillin.fillin.data.response

data class ResponseStudioLocation(
    val studio: List<Studio>,
) {
    data class Studio(
        val id: Int,
        val lati: Double,
        val long: Double
    )
}