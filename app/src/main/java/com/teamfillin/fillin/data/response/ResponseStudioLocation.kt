package com.teamfillin.fillin.data.response

import com.teamfillin.fillin.domain.entity.StudioPosition
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
    ) {
        fun toStudioMap(): StudioPosition {
            return  StudioPosition(id, lati, long)
        }
    }
}