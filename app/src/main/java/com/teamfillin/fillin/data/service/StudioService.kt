package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseStudioInfo
import retrofit2.Call
import retrofit2.http.GET

interface StudioService {
    @GET("studio")
    fun getWholeStudio(): Call<BaseResponse<ResponseStudioInfo>>
}