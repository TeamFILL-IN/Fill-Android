package com.teamfillin.fillin.presentation.filmroll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.teamfillin.fillin.data.repository.PhotoPagingRepository
import com.teamfillin.fillin.data.response.experimental.PhotoDto
import com.teamfillin.fillin.presentation.filmroll.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FilmCategoryViewModel @Inject constructor(
    private val repository: PhotoPagingRepository
) : ViewModel() {
    val photos
        get() = repository.retrievePager()
            .map { pagingData ->
                pagingData.map { it.toPhoto().toUiModel() }
            }.cachedIn(viewModelScope)
}