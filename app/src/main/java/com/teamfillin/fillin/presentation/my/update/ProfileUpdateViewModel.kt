package com.teamfillin.fillin.presentation.my.update

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamfillin.fillin.core.view.UiState
import com.teamfillin.fillin.data.response.ResponseUpdateUser
import com.teamfillin.fillin.data.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import javax.inject.Inject

@HiltViewModel
class ProfileUpdateViewModel @Inject constructor(
    private val service: AuthService
): ViewModel() {
    val nickname = MutableStateFlow("")
    val isClickable: Flow<Boolean> = nickname.map { it.isNotBlank() }

    val cameraName = MutableStateFlow("")

    private val _updateUser = MutableStateFlow<UiState<ResponseUpdateUser>>(UiState.Loading)
    val updateUser: StateFlow<UiState<ResponseUpdateUser>> = _updateUser

    private var imageBitmap: Bitmap? = null

    fun setImageBitmap(bitmap: Bitmap) {
        imageBitmap = bitmap
    }

    fun putUser() {
        val textHashMap = hashMapOf<String, RequestBody>()
        textHashMap["nickname"] = nickname.value.toRequestBody("text/plain".toMediaTypeOrNull())
        textHashMap["camera"] = cameraName.value.toRequestBody("text/plain".toMediaTypeOrNull())
        val bitmapRequestBody = imageBitmap?.let { BitmapRequestBody(it) }
        val bitmapMultipartBody = bitmapRequestBody?.let { MultipartBody.Part.createFormData("imageUrl", "imageUrl", it) }

        viewModelScope.launch {
            runCatching {
                service.putUser(bitmapMultipartBody, textHashMap)
            }.onSuccess {
                _updateUser.value = UiState.Success(it.data)
            }.onFailure {
                _updateUser.value = UiState.Failure("서버 통신 오류 : $it")
            }
        }
    }

    companion object {
        class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
            override fun contentType(): MediaType? {
                return "image/png".toMediaTypeOrNull()
            }

            override fun writeTo(sink: BufferedSink) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 99, sink.outputStream()) //99프로 압축
            }
        }
    }
}