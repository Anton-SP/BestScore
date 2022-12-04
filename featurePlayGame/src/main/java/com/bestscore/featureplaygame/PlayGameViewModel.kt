package com.bestscore.featureplaygame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestscore.core.templates.Parameter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class PlayGameViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<PlayGameUiState?> =
        MutableStateFlow(null)

    val uiState: StateFlow<PlayGameUiState?> = _uiState

//    fun setData(data: List<Parameter>) {
//        viewModelScope.launch {
//            try {
//                _uiState.emit(PlayGameUiState.Success(data))
//            } catch (e: Exception) {
//                _uiState.emit(PlayGameUiState.Error(e))
//            }
//        }
//    }

    suspend fun changeParameterValue(changeRequest: ChangeValueRequestModel) {
        viewModelScope.launch {
            _uiState.emit(PlayGameUiState.Loading)
            val data = changeRequest.data
            val index = changeRequest.index
            val parameter = changeRequest.data[index]
            val valueOfChange = changeRequest.valueOfChange
            val newValue = parameter.inGameValues.last() + valueOfChange
            data[index].inGameValues.add(newValue)
            try {
                _uiState.emit(PlayGameUiState.Success(data))
            } catch (e:Exception) {
                _uiState.emit(PlayGameUiState.Error(e))
            }
        }
    }


}