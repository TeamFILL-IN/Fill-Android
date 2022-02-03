package com.teamfillin.fillin.data.response

data class ResponseUserPhotoInfo(
    val photos:List<Photo>
) {
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