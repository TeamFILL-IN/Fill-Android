package com.teamfillin.fillin.domain.entity


data class StudioMap (
    val studios: List<StudioPosition>
) {
    data class StudioPosition(
        val id: Int,
        val lati: Double,
        val long: Double
    )
}