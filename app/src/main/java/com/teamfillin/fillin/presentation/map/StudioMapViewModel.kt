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
import com.teamfillin.fillin.domain.repository.StudioMapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.collections.set

@HiltViewModel
class StudioMapViewModel @Inject constructor(
    private val studioMapRepository: StudioMapRepository
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


    val studioIdHash = HashMap<LatLng, Int>()
    val locationHash = HashMap<Int, LatLng>()

    fun studioLocation() {
        viewModelScope.launch {
            runCatching {
                studioMapRepository.studioLocation()
            }.onSuccess {
                Timber.d(it.toString())
                _location.value = it.map { response ->
                    response.toLatLnt()
                }
                it.forEach { location ->
                    studioIdHash[location.toLatLnt()] = location.id
                    locationHash[location.id] = location.toLatLnt()
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
                studioMapRepository.studioDetail(position)
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
                studioMapRepository.studioPhoto(position)
            }.onSuccess {
                _photos.value = it
            }.onFailure{
                _serverErrorMsg.call()
                Timber.e(it)
            }
        }
    }
}