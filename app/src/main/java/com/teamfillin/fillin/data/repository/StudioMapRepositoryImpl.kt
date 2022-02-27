package com.teamfillin.fillin.data.repository

import com.teamfillin.fillin.data.service.StudioService
import com.teamfillin.fillin.domain.entity.StudioDetail
import com.teamfillin.fillin.domain.entity.StudioImage
import com.teamfillin.fillin.domain.entity.StudioMap
import com.teamfillin.fillin.domain.repository.StudioMapRepository
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject

class StudioMapRepositoryImpl @Inject constructor(
    private val service: StudioService
) : StudioMapRepository {
    override suspend fun studioLocation(): List<StudioMap.StudioPosition> {
        // return service.getWholeStudio().await().data.studios.toEntity()
        return service.getWholeStudio().await()
            .data.studios.map { response ->
                response.toStudioMap()
            }
    }

    override suspend fun studioDetail(position: Int): StudioDetail {
        return service.getStudioDetail(position).await()
            .data.studio.toStudioDetail()
    }

    override suspend fun studioPhoto(position: Int): List<StudioImage> {
        return service.getStudioPhoto(position).await()
            .data.photos.map { response ->
                response.toStudioImage()
            }
    }
}