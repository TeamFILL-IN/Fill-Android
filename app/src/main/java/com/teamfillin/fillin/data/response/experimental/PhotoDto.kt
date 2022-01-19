package com.teamfillin.fillin.data.response.experimental

import com.teamfillin.fillin.domain.entity.Photo

data class ResponseAllPhoto(
    val photos: List<PhotoDto>
)

data class PhotoDto(
    val nickname: String,
    val userImageUrl: String,
    val photoId: Int,
    val imageUrl: String,
    val filmId: Int,
    val filmName: String,
    val likeCount: Int,
    val isLiked: Boolean
) {
    fun toPhoto() =
        Photo(nickname, userImageUrl, photoId, imageUrl, filmId, filmName, likeCount, isLiked)
}
