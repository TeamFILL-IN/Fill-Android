package com.teamfillin.fillin.data.response

import com.teamfillin.fillin.domain.entity.StudioDetail

data class ResponseStudio(
    val studio: Studio
) {
    data class Studio(
        val id: Int,
        val name: String,
        val address: String,
        val price: String,
        val time: String,
        val tel: String,
        val lati: Double,
        val long: Double,
        val etc: String?,
        val isDeleted: Boolean,
        val site: String?
    )
    {
        fun toStudioDetail(): StudioDetail {
            return StudioDetail(id, name, address, price, time, tel, lati, long, etc, isDeleted, site)
        }
    }
}


