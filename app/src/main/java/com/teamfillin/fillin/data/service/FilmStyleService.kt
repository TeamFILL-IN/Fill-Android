package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseFilmPhotoInfo
import com.teamfillin.fillin.data.response.ResponseFilmStyleInfo
import retrofit2.Call
import retrofit2.http.GET

interface FilmStyleService {
    @GET("photo/style/:styleId")
    fun getFilmStyle(): Call<BaseResponse<ResponseFilmStyleInfo>>

}