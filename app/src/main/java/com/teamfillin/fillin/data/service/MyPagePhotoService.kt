package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseUserPhotoInfo
import retrofit2.Call
import retrofit2.http.GET

interface MyPagePhotoService {
    @GET("photo/user")
    fun getUserPhotos() : Call<BaseResponse<ResponseUserPhotoInfo>>
}