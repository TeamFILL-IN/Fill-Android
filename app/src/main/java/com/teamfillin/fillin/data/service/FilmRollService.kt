package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseFilmRoll
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface FilmRollService {
    @GET("photo/film/{filmId}")
    fun getFilmPhoto(@Path("filmId") filmId: Int): Call<BaseResponse<ResponseFilmRoll>>

    @GET("curation")
    fun getCuration(): Call<BaseResponse<ResponseFilmRoll>>

    @GET("photo/style/{styleId}")
    fun getFilmStyle(@Path("styleId") styleId: Int): Call<BaseResponse<ResponseFilmRoll>>

    @GET("film/{styleId}")
    fun getFilmCategory(@Path("styleId") styleId: Int): Call<BaseResponse<ResponseFilmRoll>>

    @Multipart
    @POST("photo")
    fun postImage(
        @PartMap body: HashMap<String, RequestBody>,
        @Part file: MultipartBody.Part?
    ): Call<BaseResponse<Unit>>
}
