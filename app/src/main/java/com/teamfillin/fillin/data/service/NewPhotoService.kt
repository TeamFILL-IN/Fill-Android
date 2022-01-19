package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseNewPhotoInfo
import com.teamfillin.fillin.data.response.ResponseStudioInfo
import retrofit2.Call
import retrofit2.http.GET

interface NewPhotoService {
    @GET("photo")
    fun getNewPhoto(): Call<BaseResponse<ResponseNewPhotoInfo>>

}