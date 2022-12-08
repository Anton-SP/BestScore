package com.bestscore.featuretimer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

    private val timerConfig = MutableStateFlow(TimerData())
    fun timerConfig(): StateFlow<TimerData?> = timerConfig


    suspend fun timerStart(seconds: Long) {
        viewModelScope.launch {
            if (timerConfig.value.state == TimerState.STOP) {
                timerConfig.emit(
                    timerConfig.value.copy(
                        TimerState.RUN,
                        seconds,
                        seconds
                    )
                )
            }

            if (timerConfig.value.state == TimerState.PAUSE) {
                timerConfig.emit(
                    timerConfig.value.copy(
                        TimerState.RUN
                    )
                )
            }

        }
    }

    suspend fun changeTimerState(timerData: TimerData) {
        viewModelScope.launch {
            timerConfig.emit(timerData)
        }
    }
}