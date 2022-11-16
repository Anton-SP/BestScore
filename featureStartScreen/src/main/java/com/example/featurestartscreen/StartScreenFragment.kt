package com.example.featurestartscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.featurecreatetemplate.CreateTemplateFragment
import com.example.featurestartscreen.databinding.FragmentStartScreenBinding

class StartScreenFragment:Fragment(R.layout.fragment_start_screen) {
    private val binding:FragmentStartScreenBinding by viewBinding()

    private var containerId: ViewGroup? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        containerId=container
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabDice.setOnClickListener{
            containerId?.let { container ->
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack("start")
                    .replace(container.id, CreateTemplateFragment())
                    .commit()
            }

        }
    }

}