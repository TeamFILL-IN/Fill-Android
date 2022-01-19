package com.teamfillin.fillin.data.service

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseAddPhoto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface AddPhotoService {
    @Multipart
    @POST("photo")
    fun sendPhoto(
        @Part image: MultipartBody.Part?,
        @PartMap data: HashMap<String, RequestBody>
        ) : Call<BaseResponse<ResponseAddPhoto>>
}