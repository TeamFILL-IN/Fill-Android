package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseSearchInfo
import com.teamfillin.fillin.data.response.ResponseStudioInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StudioService {
    @GET("studio")
    fun getWholeStudio(): Call<BaseResponse<ResponseStudioInfo>>

    @GET("studio/search")
    fun getSearchInfo(
        @Query("keyword") keyword: String
    ): Call<BaseResponse<ResponseSearchInfo>>
}