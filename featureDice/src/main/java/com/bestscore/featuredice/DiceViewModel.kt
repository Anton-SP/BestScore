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
    private val firstDiceState = MutableStateFlow(0)
    private val secondDiceState = MutableStateFlow(0)

    fun firstDiceState(): StateFlow<Int> = firstDiceState
    fun secondDiceState(): StateFlow<Int> = secondDiceState

    suspend fun rollDice(diceMode: DiceMode) {
        firstDiceState.value = 0
        secondDiceState.value = 0
        val turnoverCount = Random.nextInt(2,7) + 1
        roll(turnoverCount, diceMode)
    }

    private suspend fun roll(turnoverCount: Int, mode: DiceMode) {
        val maxValue =  when (mode) {
            DiceMode.MODE_1D4, DiceMode.MODE_2D4 -> 4
            DiceMode.MODE_1D6, DiceMode.MODE_2D6  -> 6
            DiceMode.MODE_1D8, DiceMode.MODE_2D8 -> 8
            DiceMode.MODE_1D12, DiceMode.MODE_2D12 -> 12
        }

        val onlyOneFirst: Boolean =
            mode == DiceMode.MODE_1D4 || mode == DiceMode.MODE_1D6 || mode == DiceMode.MODE_1D8 || mode == DiceMode.MODE_1D12

        if (onlyOneFirst) {
            viewModelScope.launch {
                repeat(turnoverCount) {
                    delay(500)
                    firstDiceState.emit(Random.nextInt(Random.nextInt(maxValue) + 1))
                }
            }
        } else {
            val firstDef = viewModelScope.async {
                repeat(turnoverCount) {
                    delay(500)
                    firstDiceState.emit(Random.nextInt(Random.nextInt(maxValue) + 1))
                }
            }

            val secondDef = viewModelScope.async {
                repeat(turnoverCount) {
                    delay(500)
                    secondDiceState.emit(Random.nextInt(Random.nextInt(maxValue) + 1))
                }

            }

            firstDef.await()
            secondDef.await()
        }

    }
}