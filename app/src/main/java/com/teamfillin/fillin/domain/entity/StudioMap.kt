package com.teamfillin.fillin.domain.entity

import com.naver.maps.geometry.LatLng

data class StudioMap (
    val studios: List<StudioPosition>
) {
    data class StudioPosition(
        val id: Int,
        val lati: Double,
        val long: Double
    ) {
        fun toLatLnt(): LatLng {
            return LatLng(lati,long)
        }
    }
}