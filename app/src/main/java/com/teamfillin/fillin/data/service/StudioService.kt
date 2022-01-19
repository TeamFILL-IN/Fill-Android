package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.*
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

    @GET("studio/detail/{studioId}")
    fun getStudioDetail(
        @Path("studioId") studioId: Int?
    ): Call<BaseResponse<ResponseStudioDetail>>

    @GET("photo/studio/{studioId}")
    fun getStudioPhoto(
        @Path("studioId") studioId: Int?
    ): Call<BaseResponse<ResponseStudioPhoto>>
}