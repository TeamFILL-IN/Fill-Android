package com.teamfillin.fillin.domain.repository

import com.teamfillin.fillin.domain.entity.StudioDetail
import com.teamfillin.fillin.domain.entity.StudioMap
import com.teamfillin.fillin.domain.entity.StudioImage

interface StudioMapRepository {
    suspend fun studioLocation(): List<StudioMap.StudioPosition>
    suspend fun studioDetail(position: Int): StudioDetail
    suspend fun studioPhoto(position: Int): List<StudioImage>
}