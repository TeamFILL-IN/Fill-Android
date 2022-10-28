package com.teamfillin.fillin.domain.repository

import com.teamfillin.fillin.domain.entity.*

interface MapRepository {
    suspend fun mapSearch(keyword: String): List<StudioAddress>
    suspend fun studioLocation(): List<StudioPosition>
    suspend fun studioDetail(position: Int): StudioDetail
    suspend fun studioPhoto(position: Int): List<StudioImage>
}