package com.teamfillin.fillin.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.teamfillin.fillin.core.content.Event
import com.teamfillin.fillin.core.content.SingleLiveEvent
import com.teamfillin.fillin.data.response.ResponseStudio
import com.teamfillin.fillin.data.response.ResponseStudioPhoto
import com.teamfillin.fillin.data.service.StudioService
import com.teamfillin.fillin.domain.entity.StudioDetail
import com.teamfillin.fillin.domain.entity.StudioSearch
import com.teamfillin.fillin.domain.repository.StudioMapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.forEach
import kotlin.collections.map
import kotlin.collections.set

@HiltViewModel
class StudioMapViewModel @Inject constructor(
    private val service: StudioService,
    private val studioMapRepository: StudioMapRepository
) : ViewModel() {

    private val _location = MutableLiveData<List<LatLng>>()
    val location: LiveData<List<LatLng>> = _location

    private val _studio = MutableLiveData<StudioDetail.Studio>()
    val studio: LiveData<StudioDetail.Studio> = _studio

    private val _photos = MutableLiveData<List<ResponseStudioPhoto.StudioPhoto>>()
    val photos: LiveData<List<ResponseStudioPhoto.StudioPhoto>> = _photos

    private val _cameraZoom = MutableLiveData<Event<Int>>()
    val cameraZoom: MutableLiveData<Event<Int>> = _cameraZoom

    private val _logIn = MutableLiveData<Event<Int>>()
    val logIn: LiveData<Event<Int>> = _logIn

    private val _serverConnect = SingleLiveEvent<Unit>()
    val serverConnect : SingleLiveEvent<Unit> = _serverConnect


    val studioIdHash = HashMap<LatLng, Int>()
    val locationHash = HashMap<Int, LatLng>()

    fun studioLocation() {
        viewModelScope.launch {
            runCatching {
                studioMapRepository.studioLocation()
            }.onSuccess {
                if (it == null) {
                    _serverConnect.call()
                } else {
                    _location.value = it.map { LatLng(it.lati, it.long) }
                    it.forEach {
                        studioIdHash[LatLng(it.lati, it.long)] = it.id
                        locationHash[it.id] = LatLng(it.lati, it.long)
                    }
                }
            }.onFailure(Timber::e)
        }
    }

    fun studioDetail(position: Int) {
//        viewModelScope.launch {
//            runCatching {
//                service.getStudioDetail(position).await()
//            }.onSuccess {
//                _studio.value = it.data.studio
//                _cameraZoom.value = Event(position)
//            }.onFailure(Timber::e)
//        }}
        viewModelScope.launch {
            runCatching {
                studioMapRepository.studioDetail(position)
            }.onSuccess {
                if (it == null) {
                    _serverConnect.call()
                } else {
                    _studio.value = it
                    _cameraZoom.value = Event(position)
                }
            }.onFailure(Timber::e)
        }
    }

    fun studioPhoto(position: Int) {
        viewModelScope.launch {
            runCatching {
                service.getStudioPhoto(position).await()
            }.onSuccess {
                _photos.value = it.data.photos
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