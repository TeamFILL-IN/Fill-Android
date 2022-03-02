package com.teamfillin.fillin.data.response

import com.teamfillin.fillin.domain.entity.StudioMap

data class ResponseStudioLocation(
    val studios: List<StudioLocation>
) {
    fun toStudioMap(): List<StudioMap.StudioPosition> {
        return studios.map {
            StudioMap.StudioPosition(it.id, it.lati, it.long)
        }
    }
}

data class StudioLocation(
    val id: Int,
    val lati: Double,
    val long: Double
)