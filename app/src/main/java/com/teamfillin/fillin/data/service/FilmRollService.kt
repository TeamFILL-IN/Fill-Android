package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseFilmRoll
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmRollService {
    @GET("photo/film/{filmId}")
    fun getFilmPhoto(@Path("filmId") filmId: Int): Call<BaseResponse<ResponseFilmRoll>>

    @GET("curation")
    fun getCuration(): Call<BaseResponse<ResponseFilmRoll>>

    @GET("photo/style/{styleId}")
    fun getFilmStyle(@Path("styleId") styleId: Int): Call<BaseResponse<ResponseFilmRoll>>

    @GET("film/{styleId}")
    fun getFilmCategory(@Path("styleId") styleId: Int): Call<BaseResponse<ResponseFilmRoll>>
}