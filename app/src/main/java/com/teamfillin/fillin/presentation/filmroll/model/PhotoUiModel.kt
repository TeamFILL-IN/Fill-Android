package com.teamfillin.fillin.presentation.filmroll.model

import com.teamfillin.fillin.domain.entity.Photo

data class PhotoUiModel(
    val imageUrl: String
)

fun Photo.toUiModel() = PhotoUiModel(imageUrl)
