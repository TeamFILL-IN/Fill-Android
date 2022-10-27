package com.teamfillin.fillin.presentation.map.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamfillin.fillin.core.view.UiState
import com.teamfillin.fillin.domain.entity.StudioSearch
import com.teamfillin.fillin.domain.repository.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapSearchViewModel @Inject constructor(
    private val mapSearchRepository: MapRepository
) : ViewModel() {

    private val _resultSearch = MutableStateFlow<UiState<List<StudioSearch.StudioAddress>>>(UiState.Loading)
    val resultSearch: StateFlow<UiState<List<StudioSearch.StudioAddress>>> = _resultSearch

    fun locationSearch(keyword: String) {
        viewModelScope.launch {
            runCatching {
                mapSearchRepository.mapSearch(keyword)
            }.onSuccess {
                Log.d("kangmi",it.toString())
                _resultSearch.value = when {
                    it.isEmpty() -> UiState.Empty
                    else -> UiState.Success(it)
                }
            }.onFailure {
                _resultSearch.value = UiState.Failure("서버 오류")
            }
        }
    }
}