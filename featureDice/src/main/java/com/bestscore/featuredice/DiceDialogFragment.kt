package com.bestscore.featuredice

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.featuredice.databinding.FragmentDialogDiceBinding

class DiceDialogFragment : DialogFragment(R.layout.fragment_dialog_dice) {
    private val binding: FragmentDialogDiceBinding by viewBinding()

    private var diceMode: DiceMode? = null
    private var selectedDiceModeViewId: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDiceModeViews()
        initRollDiceBtn()
    }

    private fun initRollDiceBtn() {
        binding.btnRollDice.setOnClickListener {
            if (diceMode == null || selectedDiceModeViewId == null) {
                Toast.makeText(requireContext(), "Выберите режим", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val view = view?.findViewById<TextView>(selectedDiceModeViewId!!)
            Toast.makeText(requireContext(), "${view!!.text}", Toast.LENGTH_SHORT).show()
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

        selectedDiceModeViewId?.let {
            requireView().findViewById<TextView>(selectedDiceModeViewId!!).background = background
        }

        diceModeView.setBackgroundColor(requireContext().getColor(com.bestscore.core.R.color.black))
        selectedDiceModeViewId = diceModeView.id
    }
}