package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.core.converter.JsonConverter
import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseAuth
import com.teamfillin.fillin.data.response.ResponseRefresh
import retrofit2.Call
import retrofit2.http.*

interface AuthService {
    @JsonConverter
    @FormUrlEncoded
    @POST("auth")
    fun login(
        @Field("token") token: String,
        @Field("social") social: String = "kakao",
        @Field("idKey") id: String
    ): Call<BaseResponse<ResponseAuth>>

    @GET("auth/token")
    fun refresh(
        @Header("accesstoken") accessToken: String,
        @Header("refreshtoken") refreshToken: String
    ): Call<BaseResponse<ResponseRefresh>>
}