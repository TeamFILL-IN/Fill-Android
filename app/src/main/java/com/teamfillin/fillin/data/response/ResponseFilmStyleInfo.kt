package com.teamfillin.fillin.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseFilmStyleInfo(
    val photos: List<FIlmStyleInfo>
) {
    @Serializable
    data class FIlmStyleInfo(
        val nickname: String,
        val userImageUrl: String,
        val photoId: Int,
        val imageUrl: String,
        val filmId: Int,
        val filmName: String,
        val likeCount: Int,
        val isLiked: Boolean
    )
}
