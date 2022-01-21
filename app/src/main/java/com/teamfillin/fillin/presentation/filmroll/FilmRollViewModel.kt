package com.teamfillin.fillin.presentation.filmroll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.teamfillin.fillin.data.remote.CategoryPhotosPagingSource
import com.teamfillin.fillin.data.service.PagingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FilmRollViewModel @Inject constructor(
    private val pagingService: PagingService
) : ViewModel() {
    fun getCategoryFilm(
        styleId: Int = -1,
        filmId: Int = -1
    ) = Pager(
        config = PagingConfig(10),
        pagingSourceFactory = { CategoryPhotosPagingSource(pagingService, styleId, filmId) }
    ).flow.map { pagingData ->
        pagingData.map { it.toPhoto() }
    }.cachedIn(viewModelScope)
}