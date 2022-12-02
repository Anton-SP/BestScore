package com.bestscore.featuredice

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.featuredice.coin.CoinViewModel
import com.bestscore.featuredice.coin.TossResult
import com.bestscore.featuredice.databinding.FragmentDialogDiceBinding
import com.bestscore.featuredice.dice.DiceMode
import com.bestscore.featuredice.dice.DiceViewModel
import kotlinx.coroutines.launch

class DiceDialogFragment : DialogFragment(R.layout.fragment_dialog_dice) {
    private val binding: FragmentDialogDiceBinding by viewBinding (createMethod = CreateMethod.INFLATE)

    private var diceMode: DiceMode? = null
    private var selectedDiceModeViewId: Int? = null

    private val diceViewModel: DiceViewModel by viewModels()
    private val coinViewModel: CoinViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)

        initDiceModeViews()
        initRollDiceButton()
        initTossCoinButton()

        collectDiceResult()
        collectCoinResult()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }

    private fun collectDiceResult() {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
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

    private fun collectCoinResult() {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            coinViewModel.coinState().collect { state ->
                state?.let {
                    when (state) {
                        is CoinViewModel.TossState.Toss -> {
                            binding.tvCoinTossResult.text = getText(R.string.toss_in_proccess)
                        }

                        is CoinViewModel.TossState.Ready -> {
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

            if (diceMode == null || selectedDiceModeViewId == null) {
                Toast.makeText(requireContext(), "Выберите режим", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            diceMode?.let { mode ->
                viewLifecycleOwner.lifecycle.coroutineScope.launch {
                    diceViewModel.rollDice(mode)
                }
            }
        }
    }

    private fun initTossCoinButton() {
        binding.btnTossCoin.setOnClickListener {
            coinViewModel.tossCoin()
        }
    }

    private fun initDiceModeViews() {
        with (binding) {
            tvDiceMode1d4.setOnClickListener {
                diceMode = DiceMode.MODE_1D4
                onClickDiceMode(diceModeView = it)
            }

            tvDiceMode1d6.setOnClickListener {
                diceMode = DiceMode.MODE_1D6
                onClickDiceMode(diceModeView = it)
            }

            tvDiceMode1d8.setOnClickListener {
                diceMode = DiceMode.MODE_1D8
                onClickDiceMode(diceModeView = it)
            }

            tvDiceMode1d12.setOnClickListener {
                diceMode = DiceMode.MODE_1D12
                onClickDiceMode(diceModeView = it)
            }

            tvDiceMode2d4.setOnClickListener {
                diceMode = DiceMode.MODE_2D4
                onClickDiceMode(diceModeView = it)
            }

            tvDiceMode2d6.setOnClickListener {
                diceMode = DiceMode.MODE_2D6
                onClickDiceMode(diceModeView = it)
            }

            tvDiceMode2d8.setOnClickListener {
                diceMode = DiceMode.MODE_2D8
                onClickDiceMode(diceModeView = it)
            }

            tvDiceMode2d12.setOnClickListener {
                diceMode = DiceMode.MODE_2D12
                onClickDiceMode(diceModeView = it)
            }
        }

    }

    private fun onClickDiceMode(diceModeView: View) {
        val background = diceModeView.background

        selectedDiceModeViewId?.let { selectedViewId ->
            val selectedView = requireView().findViewById<TextView>(selectedViewId)
            selectedView.background = background
            selectedView.setTextColor(requireContext().getColor(com.bestscore.core.R.color.white))

        }

        diceModeView.background = ContextCompat.getDrawable(requireContext(), R.drawable.selected_dice_mode)
        (diceModeView as TextView).setTextColor(requireContext().getColor(com.bestscore.core.R.color.black))
        selectedDiceModeViewId = diceModeView.id
    }
}