package com.teamfillin.fillin.data.response

data class ResponseNewPhotoInfo(
    val photo: List<Photo>,
) {
    data class Photo(
        val nickname: Int,
        val userImageUrl: String,
        val photoId: Int,
        val imageUrl: String,
        val filmId: Int,
        val filmName: String,
        val likeCount: Int

    )
}