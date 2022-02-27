package com.teamfillin.fillin.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseStudioLocation(
    val studios: List<StudioLocation>
) {
    @Serializable
    data class StudioLocation(
        val id: Int,
        val lati: Double,
        val long: Double
    )
}