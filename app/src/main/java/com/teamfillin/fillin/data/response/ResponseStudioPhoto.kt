package com.teamfillin.fillin.data.response

import com.teamfillin.fillin.domain.entity.StudioImage

data class ResponseStudioPhoto(
    val photos: List<StudioPhoto>
) {
    data class StudioPhoto(
        val studioName: String,
        val nickname: String,
        val userImageUrl: String,
        val photoId: Int,
        val imageUrl: String,
        val filmId: Int,
        val filmName: String,
        val likeCount: Int
    ) {
        fun toStudioImage() : StudioImage {
            return StudioImage(studioName, nickname, userImageUrl, photoId, imageUrl, filmId, filmName, likeCount)
        }
    }
}
