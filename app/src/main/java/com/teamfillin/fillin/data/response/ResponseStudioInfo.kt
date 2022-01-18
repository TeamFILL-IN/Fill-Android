package com.teamfillin.fillin.data.response

data class ResponseStudioInfo(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val studio: List<Studio>
    ) {
        data class Studio(
            val id: Int,
            val lati: Double,
            val long: Double
        )
    }
}