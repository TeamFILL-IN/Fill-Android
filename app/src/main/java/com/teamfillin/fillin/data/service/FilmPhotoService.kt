package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseFilmPhotoInfo
import com.teamfillin.fillin.data.response.ResponseNewPhotoInfo
import retrofit2.Call
import retrofit2.http.GET

interface FilmPhotoService {
    @GET("photo/film/:filmId")
    fun getFilmPhoto(): Call<BaseResponse<ResponseFilmPhotoInfo>>

}