package com.teamfillin.fillin.data.datasource

import com.teamfillin.fillin.data.response.ResponseUserPhotoInfo

interface PhotoDataSource {
    suspend fun getUserPhotos(): List<ResponseUserPhotoInfo.Photo>
}