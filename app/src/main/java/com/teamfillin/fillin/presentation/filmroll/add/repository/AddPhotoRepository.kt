package com.teamfillin.fillin.presentation.filmroll.add.repository

import com.teamfillin.fillin.data.response.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AddPhotoRepository {
    suspend fun registerPhoto(
        photoMetaData: HashMap<String, RequestBody>,
        image: MultipartBody.Part
    ): BaseResponse<Unit>
}