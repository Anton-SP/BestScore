package com.bestscore.featuretimer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerViewModel: ViewModel() {

    private val timerConfig = MutableStateFlow(TimerData())
    fun timerConfig(): StateFlow<TimerData?> = timerConfig


}