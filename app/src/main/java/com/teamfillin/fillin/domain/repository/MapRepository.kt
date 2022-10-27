package com.teamfillin.fillin.domain.repository

import com.teamfillin.fillin.domain.entity.StudioDetail
import com.teamfillin.fillin.domain.entity.StudioImage
import com.teamfillin.fillin.domain.entity.StudioMap
import com.teamfillin.fillin.domain.entity.StudioSearch

interface MapRepository {
    suspend fun mapSearch(keyword: String): List<StudioSearch.StudioAddress>
    suspend fun studioLocation(): List<StudioMap.StudioPosition>
    suspend fun studioDetail(position: Int): StudioDetail
    suspend fun studioPhoto(position: Int): List<StudioImage>
}