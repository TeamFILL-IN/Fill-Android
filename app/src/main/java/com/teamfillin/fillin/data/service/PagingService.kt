package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.experimental.ResponseAllPhoto
import com.teamfillin.fillin.data.response.experimental.ResponsePhotoByCategory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PagingService {
    @GET("photo")
    suspend fun retrievePhotos(@Query("pageNum") pageNum: Int): BaseResponse<ResponseAllPhoto>

    @GET("photopaging/style/{styleId}")
    suspend fun retrievePhotosByStyle(
        @Path("styleId") styleId: Int,
        @Query("pageNum") pageNum: Int
    ): BaseResponse<ResponsePhotoByCategory>

    @GET("photopaging/film/{filmId}")
    suspend fun retrievePhotosByFilm(
        @Path("filmId") filmId: Int,
        @Query("pageNum") pageNum: Int
    ): BaseResponse<ResponsePhotoByCategory>
}