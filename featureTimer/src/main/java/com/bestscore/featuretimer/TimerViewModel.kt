package com.bestscore.featuretimer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

    private val timerData = MutableStateFlow(TimerData())
    fun timerConfig(): StateFlow<TimerData?> = timerData

    suspend fun timerStart(seconds: Long) {
        viewModelScope.launch {
            if (timerData.value.state == TimerState.STOP) {
                timerData.emit(
                    timerData.value.copy(
                        TimerState.RUN,
                        seconds,
                        seconds
                    )
                )
            }

            if (timerData.value.state == TimerState.PAUSE) {
                timerData.emit(
                    timerData.value.copy(
                        TimerState.RUN
                    )
                )
            }

        }
    }

    suspend fun changeTimerState(timerData: TimerData) {
        viewModelScope.launch {
            this@TimerViewModel.timerData.emit(timerData)
        }
    }
}