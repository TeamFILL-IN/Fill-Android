package com.teamfillin.fillin.data.response

data class ResponseCurationInfo(
    val curation: Curation,
    val photo: List<CurationPhoto>
) {
    data class Curation(
        val id: Int,
        val title: String,
        val photolist: String
    )
    data class CurationPhoto(
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
