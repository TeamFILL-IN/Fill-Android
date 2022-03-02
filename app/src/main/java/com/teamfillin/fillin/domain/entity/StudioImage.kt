package com.teamfillin.fillin.domain.entity

data class StudioImage(
    val studioName: String,
    val nickname: String,
    val userImageUrl: String,
    val photoId: Int,
    val imageUrl: String,
    val filmId: Int,
    val filmName: String,
    val likeCount: Int
)