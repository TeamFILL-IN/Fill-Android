package com.teamfillin.fillin.data.response

data class ResponseFilmPhotoInfo(
    val photos: List<FilmPhotoInfo>
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
}
