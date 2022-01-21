package com.teamfillin.fillin.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.teamfillin.fillin.data.remote.PhotosPagingSource
import com.teamfillin.fillin.data.service.PagingService
import javax.inject.Inject

class PhotoPagingRepository @Inject constructor(
    private val service: PagingService
) {
    fun retrievePager() = Pager(
        config = PagingConfig(10),
        pagingSourceFactory = { PhotosPagingSource(service) }
    ).flow
}