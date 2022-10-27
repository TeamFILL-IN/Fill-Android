package com.teamfillin.fillin.data.repository

import com.teamfillin.fillin.data.datasource.MapDataSource
import com.teamfillin.fillin.domain.entity.StudioDetail
import com.teamfillin.fillin.domain.entity.StudioImage
import com.teamfillin.fillin.domain.entity.StudioMap
import com.teamfillin.fillin.domain.entity.StudioSearch
import com.teamfillin.fillin.domain.repository.MapRepository
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val dataSource: MapDataSource
) : MapRepository {
    override suspend fun mapSearch(keyword: String): List<StudioSearch.StudioAddress> {
        return dataSource.mapSearch(keyword).map {
            it.toStudioSearch()
        }
    }

    override suspend fun studioLocation(): List<StudioMap.StudioPosition> {
        return dataSource.studioLocation().map {
            it.toStudioMap()
        }
    }

    override suspend fun studioDetail(position: Int): StudioDetail {
        return dataSource.studioDetail(position).toStudioDetail()
    }

    override suspend fun studioPhoto(position: Int): List<StudioImage> {
        return dataSource.studioPhoto(position).map {
            it.toStudioImage()
        }
    }
}