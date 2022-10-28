package com.teamfillin.fillin.domain.entity

data class UserPhoto(
    val filmId: Int,
    val filmName: String,
    val imageUrl: String,
    val likeCount: Int,
    val nickname: String,
    val photoId: Int,
    val userImageUrl: String
)

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

data class CategoryPhoto(
    val nickname: String,
    val userImageUrl: String,
    val photoId: Int,
    val imageUrl: String,
    val filmId: Int,
    val filmName: String,
    val likeCount: Int,
    val isLiked: Boolean,
    val isGaro: Boolean
)
