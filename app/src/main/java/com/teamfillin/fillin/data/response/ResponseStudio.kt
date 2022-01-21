package com.teamfillin.fillin.data.response

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
        val etc: String,
        val isDeleted: Boolean,
        val site: String
    )
}


