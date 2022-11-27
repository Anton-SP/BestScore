package com.bestscore.featurecreatetemplate

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.core.templates.Parameter
import com.bestscore.core.templates.Template
import com.bestscore.featurecreatetemplate.databinding.FragmentCreateTemplateBinding
import com.bestscore.featurecreatetemplate.di.CreateTemplateComponentViewModel
import com.bestscore.utils.currentDate
import com.bestscore.utils.makeToast
import kotlinx.coroutines.flow.collect
import java.util.Date
import javax.inject.Inject

class CreateTemplateFragment : Fragment(R.layout.fragment_create_template) {

    @Inject
    internal lateinit var createTemplateViewModelFactory: dagger.Lazy<CreateTemplateViewModel.Factory>

    private val binding: FragmentCreateTemplateBinding by viewBinding()

    private val createTemplateViewModel: CreateTemplateViewModel by viewModels {
        createTemplateViewModelFactory.get()
    }

    private val adapter: ParametersAdapter by lazy {
        ParametersAdapter { position ->
            if (adapter.itemCount > 1) {
                adapter.remove(position)
            }
        }
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<CreateTemplateComponentViewModel>()
            .newCreateTemplateComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (adapter.itemCount == 0) {
            addParameter()
        }

        initRecycler()
        initFab()
        collectState()

    }

    private fun initRecycler() {
        binding.rvParams.adapter = this.adapter
        binding.rvParams.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initFab() {
        binding.fabAddParameter.setOnClickListener {
            addParameter()
        }

        binding.fabPlay.setOnClickListener {
            binding.rvParams.adapter

            createTemplateViewModel.save(
                template = Template(
                    id = 0,
                    name = binding.edTemplateName.text.toString(),
                    createdAt = currentDate()
                ), parameters = adapter.getCurrentList()
            )
        }
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            createTemplateViewModel.getState().collect { state ->
                state?.let {
                    checkState(state)
                }
            }
        }
    }

    private fun checkState(state: CreateTemplateViewModel.CreateTemplateState) {
        when(state) {
            is CreateTemplateViewModel.CreateTemplateState.Error -> {
                makeToast(state.message)
            }
            is CreateTemplateViewModel.CreateTemplateState.Success -> {
                makeToast("Шаблон успешно сохранен")
            }
        }
    }

    private fun addParameter() {
        adapter.add(
            Parameter(
                id = 0, parameterName = "", startValue = 0, takeWhenCalc = false
            )
        )
    }
}