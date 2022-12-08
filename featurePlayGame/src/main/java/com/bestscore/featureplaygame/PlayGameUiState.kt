package com.bestscore.featureplaygame

import com.bestscore.core.templates.Parameter

sealed class PlayGameUiState {
    object Started : PlayGameUiState()
    data class Success(
        val data: List<Parameter>,
        var calculatedValue: Int? = null
    ) : PlayGameUiState()

    data class Error(val err: Throwable) : PlayGameUiState()
}