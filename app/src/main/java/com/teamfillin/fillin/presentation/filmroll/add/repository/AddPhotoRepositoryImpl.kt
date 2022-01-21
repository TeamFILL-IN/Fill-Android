package com.teamfillin.fillin.presentation.filmroll.add.repository

import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.service.FilmRollService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.await
import javax.inject.Inject

class AddPhotoRepositoryImpl @Inject constructor(
    private val service: FilmRollService
) : AddPhotoRepository {
    override suspend fun registerPhoto(
        photoMetaData: HashMap<String, RequestBody>,
        image: MultipartBody.Part
    ): BaseResponse<Unit> {
        return service.postImage(photoMetaData, image).await()
    }
}