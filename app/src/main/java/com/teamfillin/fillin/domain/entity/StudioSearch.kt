package com.teamfillin.fillin.domain.entity

data class StudioSearch(
    val studios: List<StudioAddress>
) {
    data class StudioAddress(
        val id: Int,
        val name: String,
        val address: String
    )
}