package com.teamfillin.fillin.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.teamfillin.fillin.core.content.Event
import com.teamfillin.fillin.core.content.SingleLiveEvent
import com.teamfillin.fillin.domain.entity.StudioDetail
import com.teamfillin.fillin.domain.entity.StudioImage
import com.teamfillin.fillin.domain.entity.StudioPosition
import com.teamfillin.fillin.domain.repository.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.collections.set

@HiltViewModel
class StudioMapViewModel @Inject constructor(
    private val repository: MapRepository
) : ViewModel() {

    private val _location = MutableLiveData<List<LatLng>>()
    val location: LiveData<List<LatLng>> = _location

    private val _studio = MutableLiveData<StudioDetail>()
    val studio: LiveData<StudioDetail> = _studio

    private val _photos = MutableLiveData<List<StudioImage>>()
    val photos: LiveData<List<StudioImage>> = _photos

    private val _cameraZoom = MutableLiveData<Event<Int>>()
    val cameraZoom: MutableLiveData<Event<Int>> = _cameraZoom

    private val _logIn = MutableLiveData<Event<Int>>()
    val logIn: LiveData<Event<Int>> = _logIn

    private val _serverErrorMsg = SingleLiveEvent<Unit>()
    val serverErrorMsg: SingleLiveEvent<Unit> = _serverErrorMsg


    private val studioIdHash = HashMap<LatLng, Int>()
    private val locationHash = HashMap<Int, LatLng>()

    fun studioLocation() {
        viewModelScope.launch {
            runCatching {
                repository.studioLocation()
            }.onSuccess {
                Timber.d(it.toString())
                _location.value = it.map { response ->
                    response.toCoordinate()
                }
                it.forEach { location ->
                    studioIdHash[location.toCoordinate()] = location.id
                    locationHash[location.id] = location.toCoordinate()
                }
            }.onFailure {
                _serverErrorMsg.call()
                Timber.e(it)
            }
        }
    }

    fun studioDetail(position: Int) {
        viewModelScope.launch {
            runCatching {
                repository.studioDetail(position)
            }.onSuccess {
                _studio.value = it
                _cameraZoom.value = Event(position)
            }.onFailure {
                _serverErrorMsg.call()
                Timber.e(it)
            }
        }
    }

    fun studioPhoto(position: Int) {
        viewModelScope.launch {
            runCatching {
                repository.studioPhoto(position)
            }.onSuccess {
                _photos.value = it
            }.onFailure{
                _serverErrorMsg.call()
                Timber.e(it)
            }
        }
    }

    fun markerId(position: LatLng) = studioIdHash[position]

    fun markerPosition(id: Int) = locationHash[id]

    private fun StudioPosition.toCoordinate() = LatLng(lati, long)
}