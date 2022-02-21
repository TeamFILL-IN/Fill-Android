package com.teamfillin.fillin.data.response

import com.teamfillin.fillin.domain.entity.StudioSearch

data class ResponseSearch(
    val studios: List<StudioResponse>
) {
    data class StudioResponse(
        val id: Int,
        val name: String,
        val address: String
    ) {
        fun toStudioSearch(): StudioSearch.StudioAddress {
            return StudioSearch.StudioAddress(id,name,address)
    }
    }
}
