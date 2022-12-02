package com.bestscore.featuredice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class DiceViewModel : ViewModel() {
    private val diceState = MutableStateFlow(RollResult())
    fun diceState(): StateFlow<RollResult?> = diceState

    private val tossState = MutableStateFlow<TossState?>(null)
    fun coinState(): StateFlow<TossState?> = tossState

    suspend fun rollDice(diceMode: DiceMode) {
        diceState.value = RollResult()
        roll(diceMode)
    }

    fun tossCoin() {
        viewModelScope.launch {
            tossState.emit(TossState.Toss)
            delay(COIN_DELAY)
            val random = Random.nextInt(0, 2)
            val result = if (random == 1) TossResult.HEADS else TossResult.TAILS
            tossState.emit(TossState.Ready(result = result))
        }
    }

    private suspend fun roll(mode: DiceMode) {
        val maxValue =  when (mode) {
            DiceMode.MODE_1D4, DiceMode.MODE_2D4 -> D4_MAX_RANDOM_VALUE
            DiceMode.MODE_1D6, DiceMode.MODE_2D6 -> D6_MAX_RANDOM_VALUE
            DiceMode.MODE_1D8, DiceMode.MODE_2D8 -> D8_MAX_RANDOM_VALUE
            DiceMode.MODE_1D12, DiceMode.MODE_2D12 -> D12_MAX_RANDOM_VALUE
        }

        val onlyOneFirst: Boolean =
            mode == DiceMode.MODE_1D4 || mode == DiceMode.MODE_1D6 || mode == DiceMode.MODE_1D8 || mode == DiceMode.MODE_1D12

        if (onlyOneFirst) {
            viewModelScope.launch {
                val turnoverCount = Random.nextInt(MIN_TURNS_COUNT, MAX_TURNS_COUNT)
                repeat(turnoverCount) {
                    delay(DICE_DELAY)
                    diceState.emit(
                        RollResult(firstDice = Random.nextInt(1, maxValue))
                    )
                }
            }
        } else {
            val firstDef = viewModelScope.async {
                val turnoverCount = Random.nextInt(MIN_TURNS_COUNT,MAX_TURNS_COUNT)
                repeat(turnoverCount) {
                    delay(DICE_DELAY)
                    diceState.emit(
                        diceState.value.copy(
                            firstDice = Random.nextInt(1, maxValue)
                        )
                    )
                }
            }

            val secondDef = viewModelScope.async {
                val turnoverCount = Random.nextInt(MIN_TURNS_COUNT, MAX_TURNS_COUNT)
                repeat(turnoverCount) {
                    delay(DICE_DELAY)
                    diceState.emit(
                        diceState.value.copy(
                            secondDice = Random.nextInt(1, maxValue)
                        )
                    )
                }
            }

            firstDef.await()
            secondDef.await()
        }

    }

    sealed class TossState {
        object Toss : TossState()
        data class Ready(val result: TossResult) : TossState()
    }

    companion object {
        const val COIN_DELAY = 1500L
        const val DICE_DELAY = 100L

        const val MIN_TURNS_COUNT = 2
        const val MAX_TURNS_COUNT = 8

        const val D4_MAX_RANDOM_VALUE = 5
        const val D6_MAX_RANDOM_VALUE = 7
        const val D8_MAX_RANDOM_VALUE = 9
        const val D12_MAX_RANDOM_VALUE = 13
    }
}