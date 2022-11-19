package com.example.featurestartscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.core.navigation.navigate
import com.bestscore.utils.makeSnackbar
import com.example.featurestartscreen.databinding.FragmentStartScreenBinding

class StartScreenFragment : Fragment(R.layout.fragment_start_screen) {
    private val binding: FragmentStartScreenBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonCreateTemplate.setOnClickListener {
                navigate(R.id.action_startScreenFragment_to_createTemplateFragment)
            }
            buttonMyTemplates.setOnClickListener {
                navigate(R.id.action_startScreenFragment_to_templatesListFragment)
            }
            fabDice.setOnClickListener {
                root.makeSnackbar("Фича в разработке")
            }
            fabTimer.setOnClickListener {
                root.makeSnackbar("Фича в разработке")
            }
        }
    }
}