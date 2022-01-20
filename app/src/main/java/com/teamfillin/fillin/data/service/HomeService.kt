package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseNewPhotoInfo
import com.teamfillin.fillin.data.response.ResponseStudioInfo
import com.teamfillin.fillin.data.response.ResponseUserInfo
import retrofit2.Call
import retrofit2.http.GET

interface HomeService {
    @GET("photo/latest")
    fun getNewPhoto(): Call<BaseResponse<ResponseNewPhotoInfo>>

    @GET("user")
    fun getUser(): Call<BaseResponse<ResponseUserInfo>>

}