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

    suspend fun rollDice(diceMode: DiceMode) {
        diceState.value = RollResult()
        roll(diceMode)
    }

    private suspend fun roll(mode: DiceMode) {
        val maxValue =  when (mode) {
            DiceMode.MODE_1D4, DiceMode.MODE_2D4 -> 5
            DiceMode.MODE_1D6, DiceMode.MODE_2D6  -> 7
            DiceMode.MODE_1D8, DiceMode.MODE_2D8 -> 9
            DiceMode.MODE_1D12, DiceMode.MODE_2D12 -> 13
        }

        val onlyOneFirst: Boolean =
            mode == DiceMode.MODE_1D4 || mode == DiceMode.MODE_1D6 || mode == DiceMode.MODE_1D8 || mode == DiceMode.MODE_1D12

        if (onlyOneFirst) {
            viewModelScope.launch {
                val turnoverCount = Random.nextInt(2,8)
                repeat(turnoverCount) {
                    delay(500)
                    diceState.emit(
                        RollResult(firstDice = Random.nextInt(1, maxValue))
                    )
                }
            }
        } else {
            val firstDef = viewModelScope.async {
                val turnoverCount = Random.nextInt(2,8)
                repeat(turnoverCount) {
                    delay(500)
                    diceState.emit(
                        diceState.value.copy(
                            firstDice = Random.nextInt(1, maxValue)
                        )
                    )
                }
            }

            val secondDef = viewModelScope.async {
                val turnoverCount = Random.nextInt(2,8)
                repeat(turnoverCount) {
                    delay(500)
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
}