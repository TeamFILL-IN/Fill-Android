package com.teamfillin.fillin.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamfillin.fillin.core.content.SingleLiveEvent
import com.teamfillin.fillin.domain.entity.StudioSearch
import com.teamfillin.fillin.domain.repository.MapSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MapSearchViewModel @Inject constructor(private val mapSearchRepository: MapSearchRepository): ViewModel() {
    private val _studioSearch = MutableLiveData<List<StudioSearch.StudioAddress>>()
    val studioSearch: LiveData<List<StudioSearch.StudioAddress>> get() = _studioSearch

    private val _serverConnect = SingleLiveEvent<Unit>()
    val serverConnect: LiveData<Unit> get() = _serverConnect

    fun locationSearch(keyword: String) {
        viewModelScope.launch {
            runCatching {
                mapSearchRepository.mapSearch(keyword)
            }.onSuccess {
                if (it == null) {
                    _serverConnect.call()
                } else {
                    _studioSearch.value = it
                }
            }. onFailure(Timber::e)
        }

    }
}