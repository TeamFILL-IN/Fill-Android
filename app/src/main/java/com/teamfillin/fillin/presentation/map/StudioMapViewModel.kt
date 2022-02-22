package com.teamfillin.fillin.presentation.map

import androidx.lifecycle.*
import com.naver.maps.geometry.LatLng
import com.teamfillin.fillin.core.content.Event
import com.teamfillin.fillin.core.content.SingleLiveEvent
import com.teamfillin.fillin.data.response.ResponseStudio
import com.teamfillin.fillin.data.response.ResponseStudioPhoto
import com.teamfillin.fillin.data.service.StudioService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StudioMapViewModel @Inject constructor(
    private val service: StudioService
) : ViewModel() {

    private val _location = MutableLiveData<LatLng>()
    val location: LiveData<LatLng> = _location

    private val _studio = MutableLiveData<ResponseStudio.Studio>()
    val studio: LiveData<ResponseStudio.Studio> = _studio

    private val _photos = MutableLiveData<List<ResponseStudioPhoto.StudioPhoto>>()
    val photos: LiveData<List<ResponseStudioPhoto.StudioPhoto>> = _photos

    private val _cameraZoom = MutableLiveData<Event<Int>>()
    val cameraZoom: MutableLiveData<Event<Int>> = _cameraZoom

    private val _logIn = MutableLiveData<Event<Int>>()
    val logIn: LiveData<Event<Int>> get() = _logIn


    val studioIdHash = HashMap<LatLng, Int>()
    val locationHash = HashMap<Int, LatLng>()

    fun studioLocation() {
        viewModelScope.launch {
            runCatching {
                service.getWholeStudio().await()
            }.onSuccess {
                it.data.studios.forEach {
                    _location.value = LatLng(it.lati, it.long)
                    studioIdHash[_location.value!!] = it.id
                    locationHash[it.id] = _location.value!!
                }
            }.onFailure(Timber::e)
        }
    }

    fun studioDetail(position: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                service.getStudioDetail(position).await()
            }.onSuccess {
                _studio.value = it.data.studio
                _cameraZoom.value = Event(position)
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
}

