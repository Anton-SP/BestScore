package com.bestscore.featuredice.coin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class CoinViewModel : ViewModel() {
    private val tossState = MutableStateFlow<TossState?>(null)
    fun coinState(): StateFlow<TossState?> = tossState

    fun tossCoin() {
        viewModelScope.launch {
            tossState.emit(TossState.Toss)
            delay(2000)
            val random = Random.nextInt(0, 2)
            val result = if (random == 1) TossResult.HEADS else TossResult.TAILS
            tossState.emit(TossState.Ready(result = result))
        }
    }

    sealed class TossState {
        object Toss : TossState()
        data class Ready(val result: TossResult) : TossState()
    }
}