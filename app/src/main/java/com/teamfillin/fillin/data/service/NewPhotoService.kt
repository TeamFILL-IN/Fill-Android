package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseNewPhotoInfo
import com.teamfillin.fillin.data.response.ResponseStudioInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NewPhotoService {
    @GET("photo/latest")
    fun getNewPhoto(): Call<BaseResponse<ResponseNewPhotoInfo>>

    @Multipart
    @POST("photo")
    fun postImage(
        @PartMap body: HashMap<String, RequestBody>,
        @Part file: MultipartBody.Part?
    ): Call<BaseResponse<Unit>>
}