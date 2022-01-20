package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseCurationInfo
import com.teamfillin.fillin.data.response.ResponseFilmStyleInfo
import retrofit2.Call
import retrofit2.http.GET

interface CurationService {
    @GET("curation")
    fun getCuration(): Call<BaseResponse<ResponseCurationInfo>>
}