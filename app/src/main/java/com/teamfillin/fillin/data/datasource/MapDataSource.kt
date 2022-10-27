package com.teamfillin.fillin.data.datasource

import com.teamfillin.fillin.data.response.ResponseSearch
import com.teamfillin.fillin.data.response.ResponseStudio
import com.teamfillin.fillin.data.response.ResponseStudioLocation
import com.teamfillin.fillin.data.response.ResponseStudioPhoto
import com.teamfillin.fillin.domain.entity.StudioImage

interface MapDataSource {
    suspend fun mapSearch(keyword: String): List<ResponseSearch.StudioResponse>
    suspend fun studioLocation(): List<ResponseStudioLocation.StudioLocation>
    suspend fun studioDetail(position: Int): ResponseStudio.Studio
    suspend fun studioPhoto(position: Int): List<ResponseStudioPhoto.StudioPhoto>
}