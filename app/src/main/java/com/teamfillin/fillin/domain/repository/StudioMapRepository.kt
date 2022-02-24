package com.teamfillin.fillin.domain.repository

import com.teamfillin.fillin.domain.entity.StudioMap

interface StudioMapRepository {
    suspend fun studioLocation() : List<StudioMap.StudioPosition>?
}