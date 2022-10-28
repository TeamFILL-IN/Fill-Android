package com.teamfillin.fillin.data.response

import com.teamfillin.fillin.domain.entity.StudioAddress
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSearch(
    val studios: List<StudioResponse>
) {
    @Serializable
    data class StudioResponse(
        val id: Int,
        val name: String,
        val address: String
    ) {
        fun toStudioSearch(): StudioAddress {
            return StudioAddress(id, name, address)
        }
    }
}
