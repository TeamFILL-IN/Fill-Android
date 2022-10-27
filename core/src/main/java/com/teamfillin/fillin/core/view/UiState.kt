package com.teamfillin.fillin.core.view

sealed interface UiState<out T> {
    object Empty : UiState<Nothing>

    object Loading : UiState<Nothing>

    data class Success<T>(
        val data: T,
    ) : UiState<T>

    data class Failure(
        val msg: String,
    ) : UiState<Nothing>

    fun getUiStateModel(): UiStateModel {
        return UiStateModel(
            this is Empty,
            this is Loading,
            this is Success,
            this is Failure
        )
    }
}

data class UiStateModel(
    val isEmpty: Boolean,
    val isLoading: Boolean,
    val isSuccess: Boolean,
    val isFailure: Boolean
) {
    constructor() : this(false, true, false, false)
}