package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseNewPhotoInfo
import com.teamfillin.fillin.data.response.ResponseStudioInfo
import com.teamfillin.fillin.data.response.ResponseUserInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface HomeService {
    @GET("photo/latest")
    fun getNewPhoto(): Call<BaseResponse<ResponseNewPhotoInfo>>

    @GET("user")
    fun getUser(): Call<BaseResponse<ResponseUserInfo>>

    @Multipart
    @POST("photo")
    fun postImage(
        @PartMap body: HashMap<String, RequestBody>,
        @Part file: MultipartBody.Part?
    ): Call<BaseResponse<Unit>>
}