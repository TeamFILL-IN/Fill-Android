package com.teamfillin.fillin.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamfillin.fillin.domain.entity.StudioSearch
import com.teamfillin.fillin.domain.repository.MapSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MapSearchViewModel @Inject constructor(
    private val mapSearchRepository: MapSearchRepository
) : ViewModel() {

    private val _resultSearch = MutableLiveData<StudioSearchState>(StudioSearchState.Init)
    val resultSearch: LiveData<StudioSearchState> get() = _resultSearch

    fun locationSearch(keyword: String) {
        viewModelScope.launch {
            runCatching {
                mapSearchRepository.mapSearch(keyword)
            }.onSuccess {
                _resultSearch.value = when {
                    it?.isEmpty() == true -> StudioSearchState.Empty
                    it?.isNotEmpty() == true -> StudioSearchState.Studios(it)
                    else -> StudioSearchState.Failure
                }
            }.onFailure(Timber::e)
        }
    }

    sealed class StudioSearchState {
        object Init : StudioSearchState()
        object Empty : StudioSearchState()
        data class Studios(val studios: List<StudioSearch.StudioAddress>) : StudioSearchState()
        object Failure : StudioSearchState()
    }
}