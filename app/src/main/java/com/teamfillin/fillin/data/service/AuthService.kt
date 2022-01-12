package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseAuth
import com.teamfillin.fillin.data.response.ResponseRefresh
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("auth")
    fun login(@Body request: HashMap<String, String>): Call<BaseResponse<ResponseAuth>>

    @GET("auth/token")
    fun refresh(
        @Header("accesstoken") accessToken: String,
        @Header("refreshtoken") refreshToken: String
    ): Call<BaseResponse<ResponseRefresh>>
}