package com.teamfillin.fillin.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserPhotoInfo(
    val photos:List<Photo>
) {
    @Serializable
    data class Photo(
        val filmId: Int,
        val filmName: String,
        val imageUrl: String,
        val likeCount: Int,
        val nickname: String,
        val photoId: Int,
        val userImageUrl: String
    )
}