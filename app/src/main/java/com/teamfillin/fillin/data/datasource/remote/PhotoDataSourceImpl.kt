package com.teamfillin.fillin.data.datasource.remote

import com.teamfillin.fillin.data.datasource.PhotoDataSource
import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseUserPhotoInfo
import com.teamfillin.fillin.data.service.MyPagePhotoService
import javax.inject.Inject

class PhotoDataSourceImpl @Inject constructor(
    private val service: MyPagePhotoService
): PhotoDataSource {
    override suspend fun getUserPhotos(): List<ResponseUserPhotoInfo.Photo> {
        return service.getUserPhotos().data.photos
    }
}