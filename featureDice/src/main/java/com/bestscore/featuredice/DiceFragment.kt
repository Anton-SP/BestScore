package com.bestscore.featuredice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.featuredice.databinding.FragmentDiceBinding

class DiceFragment : Fragment(R.layout.fragment_dice) {
    private val binding: FragmentDiceBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}