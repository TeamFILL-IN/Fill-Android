package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StudioService {
    @GET("studio")
    suspend fun getWholeStudio(): BaseResponse<ResponseStudioLocation>

    @GET("studio/search")
    suspend fun getSearchInfo(
        @Query("keyword") keyword: String
    ): BaseResponse<ResponseSearch>

    @GET("studio/detail/{studioId}")
    suspend fun getStudioDetail(
        @Path("studioId") studioId: Int
    ): BaseResponse<ResponseStudio>

    @GET("photo/studio/{studioId}")
    suspend fun getStudioPhoto(
        @Path("studioId") studioId: Int
    ): BaseResponse<ResponseStudioPhoto>
}