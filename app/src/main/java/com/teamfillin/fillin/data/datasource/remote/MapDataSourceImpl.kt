package com.teamfillin.fillin.data.datasource.remote

import com.teamfillin.fillin.data.datasource.MapDataSource
import com.teamfillin.fillin.data.response.ResponseSearch
import com.teamfillin.fillin.data.response.ResponseStudio
import com.teamfillin.fillin.data.response.ResponseStudioLocation
import com.teamfillin.fillin.data.response.ResponseStudioPhoto
import com.teamfillin.fillin.data.service.StudioService
import javax.inject.Inject

class MapDataSourceImpl @Inject constructor(
    private val service: StudioService
): MapDataSource {
    override suspend fun mapSearch(keyword: String): List<ResponseSearch.StudioResponse> {
        return service.getSearchInfo(keyword).data.studios
    }

    override suspend fun studioLocation(): List<ResponseStudioLocation.StudioLocation> {
        return service.getWholeStudio().data.studios
    }

    override suspend fun studioDetail(position: Int): ResponseStudio.Studio {
        return service.getStudioDetail(position).data.studio
    }

    override suspend fun studioPhoto(position: Int): List<ResponseStudioPhoto.StudioPhoto> {
        return service.getStudioPhoto(position).data.photos
    }
}