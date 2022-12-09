package com.bestscore.featurecreatetemplate

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.core.navigation.navigate
import com.bestscore.core.navigation.navigationData
import com.bestscore.core.templates.Parameter
import com.bestscore.core.templates.Template
import com.bestscore.featurecreatetemplate.databinding.FragmentCreateTemplateBinding
import com.bestscore.featurecreatetemplate.di.CreateTemplateComponentViewModel
import com.bestscore.utils.currentDate
import com.bestscore.utils.makeToast
import kotlinx.coroutines.launch
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

    private var isEdit = false
    private var editableTemplate: Template? = null

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

        arguments?.let { args ->
            isEdit = args.getBoolean("is_edit", false)
            editableTemplate = args.getParcelable("template") as Template?

            editableTemplate?.let { template ->
                binding.edTemplateName.setText(template.name)
                adapter.updateParameters(template.parameters)
            }
        }

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
            if (editableTemplate != null && isEdit) {
                val copy = editableTemplate!!.copy(
                    name = binding.edTemplateName.text.toString(),
                    parameters = adapter.getCurrentList()
                )
                createTemplateViewModel.save(copy)
            } else {
                val template = Template(
                    id = 0,
                    name = binding.edTemplateName.text.toString(),
                    createdAt = currentDate(),
                    parameters = adapter.getCurrentList()
                )
                createTemplateViewModel.save(
                    template = template
                )
            }


        }
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                createTemplateViewModel.getState().collect { state ->
                    state?.let {
                        checkState(state)
                    }
                }
            }
        }
    }

    private fun checkState(state: CreateTemplateViewModel.CreateTemplateState) {
        when (state) {
            is CreateTemplateViewModel.CreateTemplateState.Error -> {
                makeToast(state.message)
            }
            is CreateTemplateViewModel.CreateTemplateState.Success -> {
                makeToast("Шаблон успешно сохранен")
                navigate(R.id.action_createTemplateFragment_to_playGameFragment, state.template)
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