package com.teamfillin.fillin.data.repository

import com.teamfillin.fillin.data.service.StudioService
import com.teamfillin.fillin.domain.entity.StudioSearch
import com.teamfillin.fillin.domain.repository.MapSearchRepository
import retrofit2.Response
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject

class MapSearchRepositoryImpl @Inject constructor(
    private val service: StudioService
) : MapSearchRepository {
    override suspend fun mapSearch(keyword: String): List<StudioSearch.StudioAddress>? {
        runCatching {
            service.getSearchInfo(keyword).await()
        }.fold({
            Timber.e(it.toString())
            return it.data.studios.map { response ->
                response.toStudioSearch()
            }
        }, { return null })
    }
}