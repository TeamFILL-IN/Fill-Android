package com.teamfillin.fillin.presentation.filmroll.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamfillin.fillin.core.content.ContentUriRequestBody
import com.teamfillin.fillin.presentation.filmroll.add.repository.AddPhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddPhotoViewModel @Inject constructor(
    private val repository: AddPhotoRepository
) : ViewModel() {
    private val _film: MutableStateFlow<FilmUiState> = MutableStateFlow(FilmUiState.NotDetermined)
    val film: StateFlow<FilmUiState> = _film.asStateFlow()
    private val _studio: MutableStateFlow<StudioUiState> =
        MutableStateFlow(StudioUiState.NotDetermined)
    val studio: StateFlow<StudioUiState> = _studio.asStateFlow()
    private val _image: MutableStateFlow<ImageUiState> = MutableStateFlow(ImageUiState.Init)
    val image: StateFlow<ImageUiState> = _image.asStateFlow()
    val isClickable: Flow<Boolean> =
        combine(film, image, studio) { filmUiState, imageUiState, studioUiState ->
            filmUiState != FilmUiState.NotDetermined && imageUiState != ImageUiState.Init && studioUiState != StudioUiState.NotDetermined
        }
    private val _registerSuccess = MutableSharedFlow<Unit>(replay = 0)
    val registerSuccess = _registerSuccess.asSharedFlow()

    fun setFilmMetaData(id: Int, name: String) {
        _film.value = FilmUiModel(id, name).toUiState()
    }

    fun setStudio(id: Int, name: String) {
        _studio.value = StudioUiModel(id, name).toUiState()
    }

    fun setUri(requestBody: ContentUriRequestBody) {
        _image.value = requestBody.toUiState()
    }

    fun registerPhoto() {
        viewModelScope.launch {
            runCatching {
                repository.registerPhoto(
                    hashMapOf(
                           "studioId" to (studio.value as StudioUiState.Determined).studio.id.toRequestBody(),
                        "filmId" to (film.value as FilmUiState.Determined).film.id.toRequestBody()
                    ),
                    (image.value as ImageUiState.Selected).image.toFormData()
                )
            }.onSuccess { _registerSuccess.emit(Unit) }.onFailure(Timber::e)
        }
    }

    private fun Int.toRequestBody() = toString().toRequestBody("text/plain".toMediaTypeOrNull())

    private fun ContentUriRequestBody.toUiState() = ImageUiState.Selected(this)

    sealed class ImageUiState {
        object Init : ImageUiState()
        data class Selected(val image: ContentUriRequestBody) : ImageUiState()
    }

    sealed class FilmUiState {
        object NotDetermined : FilmUiState()
        data class Determined(val film: FilmUiModel) : FilmUiState()
    }

    sealed class StudioUiState {
        object NotDetermined : StudioUiState()
        data class Determined(val studio: StudioUiModel) : StudioUiState()
    }
}

data class FilmUiModel(
    val id: Int,
    val name: String
) {
    fun toUiState() = AddPhotoViewModel.FilmUiState.Determined(this)
}

data class StudioUiModel(
    val id: Int,
    val name: String
) {
    fun toUiState() = AddPhotoViewModel.StudioUiState.Determined(this)
}