package com.teamfillin.fillin.domain.entity

data class Photo(
    val nickname: String,
    val userImageUrl: String,
    val photoId: Int,
    val imageUrl: String,
    val filmId: Int,
    val fileName: String,
    val likeCount: Int,
    val isLiked: Boolean
)
