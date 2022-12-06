package com.bestscore.featuredice

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.featuredice.databinding.FragmentDialogDiceBinding
import com.bestscore.utils.makeToast
import kotlinx.coroutines.launch

class DiceDialogFragment : DialogFragment(R.layout.fragment_dialog_dice) {
    private val binding: FragmentDialogDiceBinding by viewBinding (createMethod = CreateMethod.INFLATE)

    private var diceMode: DiceMode = DiceMode.MODE_1D6
    private var selectedDiceModeViewId: Int? = null

    private val diceViewModel: DiceViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)

        selectDiceMode(binding.tvDiceMode1d6)

        initDiceModeViews()
        initRollDiceButton()
        initTossCoinButton()
        initCloseButton()

        collectDiceResult()
        collectCoinResult()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setView(binding.root)
            .create()
    }

    private fun collectDiceResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                diceViewModel.diceState().collect { rollResult ->
                    rollResult?.let { result ->
                        if (result.firstDice != 0) {
                            binding.firstDiceResult.text = result.firstDice.toString()
                        }

                        if (result.secondDice != 0) {
                            binding.hyphen.isVisible = true
                            binding.secondDiceResult.text = result.secondDice.toString()
                        } else {
                            binding.hyphen.isVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun collectCoinResult() {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            diceViewModel.coinState().collect { state ->
                state?.let {
                    when (state) {
                        is DiceViewModel.TossState.Toss -> {
                            binding.tvCoinTossResult.text = getText(R.string.toss_in_proccess)
                        }

                        is DiceViewModel.TossState.Ready -> {
                            val text = if (state.result == TossResult.HEADS) getString(R.string.heads) else getString(R.string.tails)
                            binding.tvCoinTossResult.text = text
                        }
                    }
                }
            }
        }
    }

    private fun initRollDiceButton() {
        binding.btnRollDice.setOnClickListener {
            with(binding) {
                firstDiceResult.text = null
                hyphen.isVisible = false
                secondDiceResult.text = null
            }

            if (selectedDiceModeViewId == null) {
                makeToast("Выберите режим")
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycle.coroutineScope.launch {
                diceViewModel.rollDice(diceMode)
            }
        }
    }

    private fun initTossCoinButton() {
        binding.btnTossCoin.setOnClickListener {
            diceViewModel.tossCoin()
        }
    }

    private fun initCloseButton() {
        binding.btnCloseDialog.setOnClickListener {
            dialog?.cancel()
        }
    }

    private fun initDiceModeViews() {
        with (binding) {
            tvDiceMode1d4.setOnClickListener {
                diceMode = DiceMode.MODE_1D4
                selectDiceMode(diceModeView = it)
            }

            tvDiceMode1d6.setOnClickListener {
                diceMode = DiceMode.MODE_1D6
                selectDiceMode(diceModeView = it)
            }

            tvDiceMode1d8.setOnClickListener {
                diceMode = DiceMode.MODE_1D8
                selectDiceMode(diceModeView = it)
            }

            tvDiceMode1d12.setOnClickListener {
                diceMode = DiceMode.MODE_1D12
                selectDiceMode(diceModeView = it)
            }

            tvDiceMode2d4.setOnClickListener {
                diceMode = DiceMode.MODE_2D4
                selectDiceMode(diceModeView = it)
            }

            tvDiceMode2d6.setOnClickListener {
                diceMode = DiceMode.MODE_2D6
                selectDiceMode(diceModeView = it)
            }

            tvDiceMode2d8.setOnClickListener {
                diceMode = DiceMode.MODE_2D8
                selectDiceMode(diceModeView = it)
            }

            tvDiceMode2d12.setOnClickListener {
                diceMode = DiceMode.MODE_2D12
                selectDiceMode(diceModeView = it)
            }
        }

    }

    private fun selectDiceMode(diceModeView: View) {
        val background = diceModeView.background

        selectedDiceModeViewId?.let { selectedViewId ->
            val selectedView = binding.root.findViewById<TextView>(selectedViewId)
            selectedView.background = background
            selectedView.setTextColor(requireContext().getColor(com.bestscore.core.R.color.white))
        }

        diceModeView.background = ContextCompat.getDrawable(requireContext(), R.drawable.selected_dice_mode)
        (diceModeView as TextView).setTextColor(requireContext().getColor(com.bestscore.core.R.color.black))
        selectedDiceModeViewId = diceModeView.id
    }
}