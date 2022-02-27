package com.teamfillin.fillin.data.response

import com.teamfillin.fillin.domain.entity.StudioSearch
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
        fun toStudioSearch(): StudioSearch.StudioAddress {
            return StudioSearch.StudioAddress(id, name, address)
        }
    }
}
