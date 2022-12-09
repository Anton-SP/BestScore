package com.bestscore.featureplaygame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestscore.core.templates.Parameter
import com.bestscore.core.templates.Template
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class PlayGameViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<PlayGameUiState> =
        MutableStateFlow(PlayGameUiState.Started)

    val uiState: StateFlow<PlayGameUiState> = _uiState

    fun setData(data: Template?) {
        if (data != null) {
            viewModelScope.launch {
                try {
                    _uiState.emit(
                        PlayGameUiState.Success(
                            data = data.parameters,
                            calculatedValue = calculateTotalScore(data.parameters)
                        )
                    )
                } catch (e: Exception) {
                    _uiState.emit(PlayGameUiState.Error(e))
                }
            }
        }
    }

    suspend fun changeParameterValue(changeRequest: ChangeValueRequestModel) {
        viewModelScope.launch {
            val data = changeRequest.data
            val index = changeRequest.index
            val parameter = changeRequest.data[index]
            val valueOfChange = changeRequest.valueOfChange
            val newValue = parameter.inGameValues.last() + valueOfChange
            data[index].inGameValues.add(newValue)
            try {
                _uiState.emit(
                    PlayGameUiState.Success(
                        data = data,
                        calculatedValue = calculateTotalScore(data)
                    )
                )
            } catch (e: Exception) {
                _uiState.emit(PlayGameUiState.Error(e))
            }
        }
    }

    private fun calculateTotalScore(data: List<Parameter>): Int? {
        var calculatedScore: Int? = null
        data.forEach {
            if (it.takeWhenCalc) {
                calculatedScore = if (calculatedScore == null) {
                    it.inGameValues.last()
                } else {
                    calculatedScore!! + it.inGameValues.last()
                }
            }
        }
        return calculatedScore
    }


}