package com.bestscore.featuretimer

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.featuretimer.TimerState.*
import com.bestscore.featuretimer.databinding.FragmentTimerBinding
import com.bestscore.utils.makeToast
import kotlin.properties.Delegates

class TimerDialogFragment : DialogFragment(R.layout.fragment_timer) {
    private val binding: FragmentTimerBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private lateinit var timer: CountDownTimer
    private var timerState = Stop
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
        initPickers()
        initTimer()

        binding.fabStart.setOnClickListener { view ->
            startTimer()
        }

        binding.fabPause.setOnClickListener { view ->
            timer.cancel()
            timerState = Pause
            updateButtons()
        }

        binding.fabStop.setOnClickListener { view ->
            timer.cancel()
            onTimerFinish()
        }

        binding.buttonCloseDialog.setOnClickListener { view ->
            dialog?.cancel()
        }

    }

    private fun initPickers() {
        binding.minutesPicker.apply {
            maxValue = 59
            minValue = 0
        }
        binding.secondsPicker.apply {
            maxValue = 59
            minValue = 0
        }
    }

    private fun initTimer() {
        timerState = Stop
        timerLengthSeconds = 0L
        secondsRemaining = 0L
        updateButtons()
    }

    private fun startTimer() {
        when (timerState) {
            Pause -> {
                timerState = Run
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

            Stop -> {
                hidePickers()
                timerState = Run
                secondsRemaining = binding.minutesPicker.value * 60L + binding.secondsPicker.value
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

    private fun hidePickers() {
        binding.apply {
            minutesPicker.visibility = View.INVISIBLE
            secondsPicker.visibility = View.INVISIBLE
            timer.visibility = View.VISIBLE
            progressCircular.visibility = View.VISIBLE
        }
    }

    private fun showPickers() {
        binding.apply {
            minutesPicker.visibility = View.VISIBLE
            secondsPicker.visibility = View.VISIBLE
            timer.visibility = View.INVISIBLE
            progressCircular.visibility = View.INVISIBLE
        }
    }

    private fun setNewTimerLength() {

        if (timerState == Stop) {
            timerLengthSeconds = 0
        }
        if (timerState == Run) {
            timerLengthSeconds = secondsRemaining
        }
        binding.progressCircular.max = timerLengthSeconds.toInt()
    }

    private fun onTimerFinish() {
        showPickers()
        timerState = Stop
        setNewTimerLength()
        binding.progressCircular.progress = 0
        secondsRemaining = timerLengthSeconds
        updateButtons()
        updateCountdownUI()
    }

    private fun updateCountdownUI() {
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        binding.timer.text =
            "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
        binding.progressCircular.progress = (timerLengthSeconds - secondsRemaining).toInt()
    }

    private fun updateButtons() {
        binding.apply {
            when (timerState) {
                Run -> {
                    fabStart.isEnabled = false
                    fabPause.isEnabled = true
                    fabStop.isEnabled = true
                }
                Pause -> {
                    fabStart.isEnabled = true
                    fabPause.isEnabled = false
                    fabStop.isEnabled = true
                }
                Stop -> {
                    fabStart.isEnabled = true
                    fabPause.isEnabled = false
                    fabStop.isEnabled = false
                }
            }
        }
    }

    override fun onDestroyView() {
        if (secondsRemaining != 0L) timer.cancel()
        super.onDestroyView()
    }
}