package com.bestscore.featureplaygame

import com.bestscore.core.templates.Parameter

sealed class PlayGameUiState {
    object Loading : PlayGameUiState()
    data class Success(val data: List<Parameter>) : PlayGameUiState()
    data class Error(val err: Throwable) : PlayGameUiState()
}