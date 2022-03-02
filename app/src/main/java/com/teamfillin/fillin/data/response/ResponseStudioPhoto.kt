package com.teamfillin.fillin.data.response

import com.teamfillin.fillin.domain.entity.StudioImage

data class ResponseStudioPhoto(
    val photos: List<StudioPhoto>
) {
    fun toStudioImage(): List<StudioImage> {
        return photos.map {
            StudioImage(
                it.studioName,
                it.nickname,
                it.userImageUrl,
                it.photoId,
                it.imageUrl,
                it.filmId,
                it.filmName,
                it.likeCount
            )
        }
    }
}

data class StudioPhoto(
    val studioName: String,
    val nickname: String,
    val userImageUrl: String,
    val photoId: Int,
    val imageUrl: String,
    val filmId: Int,
    val filmName: String,
    val likeCount: Int
)
