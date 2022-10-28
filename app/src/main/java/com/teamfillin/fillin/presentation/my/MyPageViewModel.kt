package com.teamfillin.fillin.presentation.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamfillin.fillin.core.view.UiState
import com.teamfillin.fillin.domain.entity.User
import com.teamfillin.fillin.domain.entity.UserPhoto
import com.teamfillin.fillin.domain.repository.AuthRepository
import com.teamfillin.fillin.domain.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _userInfo = MutableStateFlow<UiState<User>>(UiState.Loading)
    val userInfo: StateFlow<UiState<User>> = _userInfo

    private val _myPhotos = MutableStateFlow<UiState<List<UserPhoto>>>(UiState.Loading)
    val myPhotos: StateFlow<UiState<List<UserPhoto>>> = _myPhotos

    fun getUser() {
        viewModelScope.launch {
            runCatching {
                authRepository.getUser()
            }.onSuccess {
                _userInfo.value = UiState.Success(it)
            }.onFailure {
                _userInfo.value = UiState.Failure("서버 오류")
            }
        }
    }
    fun getUserPhotos() {
        viewModelScope.launch {
            runCatching {
                photoRepository.getUserPhotos()
            }.onSuccess {
                _myPhotos.value = UiState.Success(it)
            }.onFailure {
                _myPhotos.value = UiState.Failure("서버 오류")
            }
        }
    }
}