package com.teamfillin.fillin.data.repository

import com.teamfillin.fillin.data.datasource.MapDataSource
import com.teamfillin.fillin.domain.entity.*
import com.teamfillin.fillin.domain.repository.MapRepository
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val dataSource: MapDataSource
) : MapRepository {
    override suspend fun mapSearch(keyword: String): List<StudioAddress> {
        return dataSource.mapSearch(keyword).map {
            it.toStudioSearch()
        }
    }

    override suspend fun studioLocation(): List<StudioPosition> {
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