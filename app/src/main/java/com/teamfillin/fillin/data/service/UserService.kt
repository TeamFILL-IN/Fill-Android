package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseUserInfo
import retrofit2.Call
import retrofit2.http.GET

interface UserService {
    @GET("user")
    suspend fun getUserInfo() : BaseResponse<ResponseUserInfo>
}