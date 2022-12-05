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

class TimerDialogFragment : DialogFragment(R.layout.fragment_timer) {
    private val binding: FragmentTimerBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    enum class TimerState {
        Stop, Pause, Run
    }

    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds = 0L
    private var timerState = TimerState.Stop

    private var secondsRemaining = 0L


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)

        initTimer()

        binding.fabStart.setOnClickListener { view ->
            startTimer()
            timerState = TimerState.Run
            //todo update buttons()
        }
    }

    private fun startTimer() {
       // secondsRemaining = 360L
        timerState = TimerState.Run


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

    private fun initTimer() {
        timerState = TimerState.Stop
        setNewTimerLength()
    }

    private fun setNewTimerLength() {
       timerLengthSeconds = 1*60L
        binding.progressCircular.max = timerLengthSeconds.toInt()
    }

    private fun onTimerFinish() {
        timerState = TimerState.Stop
        setNewTimerLength()
        binding.progressCircular.progress = 0
        updateCountdownUI()
    }

    private fun updateCountdownUI() {
        val minutesUntilFinished = secondsRemaining/60
        val secondsInMinuteUntilFinished = secondsRemaining-minutesUntilFinished
        val secondsStr=secondsInMinuteUntilFinished.toString()
        binding.timer.text="$minutesUntilFinished:${if(secondsStr.length==2)secondsStr else "0"+ secondsStr}"
        binding.progressCircular.progress=(timerLengthSeconds-secondsRemaining).toInt()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setView(binding.root)
            .create()
    }

}

