package com.teamfillin.fillin.data.service.experimental

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.experimental.ResponseAllPhoto
import retrofit2.http.GET
import retrofit2.http.Query

interface PagingService {
    @GET("photo")
    suspend fun retrievePhotos(@Query("pageNum") pageNum: Int): BaseResponse<ResponseAllPhoto>
}