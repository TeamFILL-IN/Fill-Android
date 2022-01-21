package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseFilmStyleInfo
import com.teamfillin.fillin.data.response.ResponseFilmType
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmStyleService {

    @GET("photo/style/:styleId")
    fun getFilmStyle(): Call<BaseResponse<ResponseFilmStyleInfo>>

    @GET("film/{styleId}")
    fun getFilmType(
        @Path("styleId") styleId: Int
    ): Call<BaseResponse<ResponseFilmType>>

}