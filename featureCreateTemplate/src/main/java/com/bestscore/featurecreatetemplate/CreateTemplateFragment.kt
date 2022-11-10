package com.bestscore.featurecreatetemplate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.core.templates.Parameter
import com.bestscore.featurecreatetemplate.databinding.FragmentCreateTemplateBinding
import com.bestscore.core.templates.Template

class CreateTemplateFragment : Fragment(R.layout.fragment_create_template) {
    private val binding: FragmentCreateTemplateBinding by viewBinding()

    private val viewModel: CreateTemplateViewModel by viewModels { CreateTemplateViewModel.Factory }

    private val adapter: ParametersAdapter by lazy {
        ParametersAdapter { position ->
            if (adapter.itemCount > 1) {
                adapter.remove(position)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (adapter.itemCount == 0) {
            addParameter()
        }

        initRecycler()
        initFab()

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

            viewModel.save(
                template = Template(
                    id = 0,
                    name = binding.edTemplateName.text.toString()
                ),
                parameters = adapter.getCurrentList()
            )
        }
    }

    private fun addParameter() {
        adapter.add(
            Parameter(
                id = 0,
                parameterName = "",
                startValue = 0,
                takeWhenCalc = false
            )
        )
    }
}