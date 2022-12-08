package com.bestscore.featuretimer

data class TimerData(
    var state: TimerState = TimerState.STOP,
    var timerLengthSeconds: Long = 0L,
    var secondsRemaining: Long = 0L
)
