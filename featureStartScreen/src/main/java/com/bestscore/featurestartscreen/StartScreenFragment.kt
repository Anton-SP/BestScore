package com.bestscore.featurestartscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.core.navigation.navigate
import com.bestscore.featurestartscreen.databinding.FragmentStartScreenBinding
import com.bestscore.utils.makeToast

class StartScreenFragment : Fragment(R.layout.fragment_start_screen) {

    private val binding: FragmentStartScreenBinding by viewBinding()

    private val adapter:StartScreenAdapter by lazy {
        StartScreenAdapter(
            onClickEdit = {
                makeToast("Переход на редатирование")
            },
            onClickDelete = {
                makeToast("Заглушка на удаление")
            }
        )
    }

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
                makeToast("Заглушка на бросок кубика")
            }
            fabTimer.setOnClickListener {
                makeToast("Заглушка на таймер")
            }
        }
    }
}