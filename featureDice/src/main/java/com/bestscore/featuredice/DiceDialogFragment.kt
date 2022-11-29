package com.bestscore.featuredice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.featuredice.databinding.FragmentDialogDiceBinding

class DiceDialogFragment : DialogFragment(R.layout.fragment_dialog_dice) {
    private val binding: FragmentDialogDiceBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}