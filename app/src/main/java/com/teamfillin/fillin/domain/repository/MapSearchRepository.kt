package com.teamfillin.fillin.domain.repository

import com.teamfillin.fillin.domain.entity.StudioSearch

interface MapSearchRepository {
    suspend fun mapSearch(keyword: String): List<StudioSearch.StudioAddress>?
}