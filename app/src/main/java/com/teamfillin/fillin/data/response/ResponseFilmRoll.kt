package com.teamfillin.fillin.data.response

data class ResponseFilmRoll(
    val photos: List<FilmPhotoInfo>,
    val curation: Curation
) {
    data class FilmPhotoInfo(
        val nickname: String,
        val userImageUrl: String,
        val photoId: Int,
        val imageUrl: String,
        val filmId: Int,
        val filmName: String,
        val likeCount: Int,
        val isLiked: Boolean
    )

    data class Curation(
        val id: Int,
        val title: String,
        val photolist: String
    )
}
