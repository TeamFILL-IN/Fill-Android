package com.teamfillin.fillin.domain.repository

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseUserPhotoInfo
import com.teamfillin.fillin.domain.entity.UserPhoto
import retrofit2.Call
import retrofit2.http.GET

interface PhotoRepository {
    suspend fun getUserPhotos() : List<UserPhoto>
}