package com.bestscore.featuretimer

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.featuretimer.databinding.FragmentTimerBinding
import com.bestscore.utils.makeToast
import kotlin.properties.Delegates

class TimerDialogFragment : DialogFragment(R.layout.fragment_timer) {
    private val binding: FragmentTimerBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    enum class TimerState {
        Stop, Pause, Run
    }

    private lateinit var timer: CountDownTimer
    private var timerState = TimerState.Stop
    private var timerLengthSeconds by Delegates.notNull<Long>()
    private var secondsRemaining by Delegates.notNull<Long>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setView(binding.root)
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)

        initTimer()

        binding.fabStart.setOnClickListener { view ->
            startTimer()

        }

        binding.fabPause.setOnClickListener { view ->
            timer.cancel()
            timerState = TimerState.Pause
            updateButtons()
        }

        binding.fabStop.setOnClickListener { view ->
            timer.cancel()
            onTimerFinish()
        }

    }

    private fun initTimer() {
        timerState = TimerState.Stop
        timerLengthSeconds = 0L
        secondsRemaining = 0L
        updateButtons()

    }

    private fun startTimer() {

        when (timerState) {
            TimerState.Pause -> {
                timerState = TimerState.Run
                updateButtons()
                setNewTimerLength()
                timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
                    override fun onFinish() {
                        onTimerFinish()
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        secondsRemaining = millisUntilFinished / 1000
                        updateCountdownUI()
                    }
                }.start()
            }

            TimerState.Stop -> {
                timerState = TimerState.Run
                secondsRemaining = 60L
                updateButtons()
                setNewTimerLength()
                timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
                    override fun onFinish() {
                        onTimerFinish()
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        secondsRemaining = millisUntilFinished / 1000
                        updateCountdownUI()
                    }
                }.start()
            }
            else -> {
                makeToast("Неверное состояние таймера")
            }
        }


    }


    private fun setNewTimerLength() {

        if (timerState == TimerState.Stop){
            timerLengthSeconds = 0
        }
        if (timerState == TimerState.Run){
            //добавть значение таймера
            timerLengthSeconds = 60L
        }

        binding.progressCircular.max = timerLengthSeconds.toInt()
    }

    private fun onTimerFinish() {
        timerState = TimerState.Stop

        setNewTimerLength()
        binding.progressCircular.progress = 0

        secondsRemaining = timerLengthSeconds

        updateButtons()
        updateCountdownUI()
    }

    private fun updateCountdownUI() {
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished
        val secondsStr = secondsInMinuteUntilFinished.toString()
        binding.timer.text =
            "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
        binding.progressCircular.progress = (timerLengthSeconds - secondsRemaining).toInt()
    }


    private fun updateButtons() {
        binding.apply {
            when (timerState) {
                TimerState.Run -> {
                    fabStart.isEnabled = false
                    fabPause.isEnabled = true
                    fabStop.isEnabled = true
                }
                TimerState.Pause -> {
                    fabStart.isEnabled = true
                    fabPause.isEnabled = false
                    fabStop.isEnabled = true
                }
                TimerState.Stop -> {
                    fabStart.isEnabled = true
                    fabPause.isEnabled = false
                    fabStop.isEnabled = false
                }
            }
        }
    }
}



