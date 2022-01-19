package com.teamfillin.fillin.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        )
}