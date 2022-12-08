package com.bestscore.featuretimer

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.featuretimer.TimerState.*
import com.bestscore.featuretimer.databinding.FragmentTimerBinding

class TimerDialogFragment : DialogFragment(R.layout.fragment_timer) {
    private val binding: FragmentTimerBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private lateinit var timer: CountDownTimer

    private val timerViewModel: TimerViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setView(binding.root)
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)
        initPickers()
        dialog?.setCanceledOnTouchOutside(false)

        collectTimerData()

        binding.fabStart.setOnClickListener { view ->
            startTimer()
        }

        binding.fabPause.setOnClickListener { view ->
            onTimerPause()
        }

        binding.fabStop.setOnClickListener { view ->
            onTimerFinish()
        }

        binding.buttonCloseDialog.setOnClickListener { view ->
            dialog?.cancel()
        }

    }

    private fun startTimer() {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            timerViewModel.apply {
                var seconds = binding.minutesPicker.value * 60L + binding.secondsPicker.value
                if (seconds != 0L) {
                    timerStart(binding.minutesPicker.value * 60L + binding.secondsPicker.value)
                }
            }
        }
    }

    private fun collectTimerData() {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            timerViewModel.timerConfig().collect() { timerData ->
                timerData?.let {
                    updateButtons(timerData.state)
                    updatePickers(timerData.state)

                    when (timerData.state) {
                        RUN -> {
                            setTimer(timerData.secondsRemaining)
                            if (binding.progressCircular.max == 0) {
                                binding.progressCircular.max = timerData.secondsRemaining.toInt()
                            }
                        }
                        STOP -> {
                            if (timerData.secondsRemaining != 0L) {
                                timer.cancel()
                            }
                            binding.progressCircular.max = 0
                        }
                        PAUSE -> {
                            timer.cancel()
                        }

                    }
                }

            }
        }
    }

    private fun setTimer(seconds: Long) {
        var secondsRemaining = seconds
        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {

            override fun onFinish() {
                onTimerFinish()
            }

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                updateCountdownUI(secondsRemaining)
            }
        }.start()
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


    private fun updatePickers(timerState: TimerState) {
        when (timerState) {
            RUN, PAUSE -> {
                binding.apply {
                    minutesPicker.visibility = View.INVISIBLE
                    secondsPicker.visibility = View.INVISIBLE
                    timer.visibility = View.VISIBLE
                    progressCircular.visibility = View.VISIBLE
                }
            }
            STOP -> {
                binding.apply {
                    minutesPicker.visibility = View.VISIBLE
                    secondsPicker.visibility = View.VISIBLE
                    timer.visibility = View.INVISIBLE
                    progressCircular.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun onTimerFinish() {
        timer.cancel()
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            timerViewModel.changeTimerState(
                TimerData(STOP)
            )
            binding.progressCircular.apply {
                progress = 0
                max = 0
            }
        }
    }

    private fun onTimerPause() {
        timer.cancel()
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            timerViewModel.changeTimerState(
                TimerData(
                    PAUSE,
                    binding.progressCircular.max.toLong(),
                    (binding.progressCircular.max - binding.progressCircular.progress).toLong()
                )
            )
        }
    }

    private fun updateCountdownUI(secondsRemaining: Long) {
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        binding.timer.text =
            "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
        binding.progressCircular.apply {
            progress = (max - secondsRemaining).toInt()
        }
    }

    private fun updateButtons(state: TimerState) {
        binding.apply {
            when (state) {
                RUN -> {
                    fabStart.isEnabled = false
                    fabPause.isEnabled = true
                    fabStop.isEnabled = true
                }
                PAUSE -> {
                    fabStart.isEnabled = true
                    fabPause.isEnabled = false
                    fabStop.isEnabled = true
                }
                STOP -> {
                    fabStart.isEnabled = true
                    fabPause.isEnabled = false
                    fabStop.isEnabled = false
                }
            }
        }
    }

    override fun onDestroyView() {
        if (binding.progressCircular.progress != 0) {
            timer.cancel()
        }
        super.onDestroyView()
    }
}