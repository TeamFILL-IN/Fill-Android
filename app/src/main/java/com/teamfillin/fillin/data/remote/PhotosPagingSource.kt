package com.teamfillin.fillin.data.remote

import com.teamfillin.fillin.core.base.OffsetPagingSource
import com.teamfillin.fillin.core.base.START_POSITION_INDEX
import com.teamfillin.fillin.data.response.experimental.PhotoDto
import com.teamfillin.fillin.data.service.PagingService

class PhotosPagingSource(
    private val service: PagingService
) : OffsetPagingSource<PhotoDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoDto> {
        val currentPosition = params.key ?: START_POSITION_INDEX

        val response = runCatching { service.retrievePhotos(currentPosition) }
            .getOrElse { return LoadResult.Error(it) }

        val nextPositon = currentPosition + 1
        val previousPosition =
            if (currentPosition != START_POSITION_INDEX) null else currentPosition - 1
        return runCatching {
            LoadResult.Page(
                data = response.data.photos,
                prevKey = previousPosition,
                nextKey = nextPositon
            )
        }.getOrElse { LoadResult.Error(it) }
    }
}