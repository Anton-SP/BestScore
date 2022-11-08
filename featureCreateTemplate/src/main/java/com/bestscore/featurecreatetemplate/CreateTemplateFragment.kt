package com.bestscore.featurecreatetemplate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bestscore.core.templates.Parameter
import com.bestscore.featurecreatetemplate.databinding.FragmentCreateTemplateBinding
import com.bestscore.core.templates.Template

class CreateTemplateFragment : Fragment() {
    private var _binding: FragmentCreateTemplateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateTemplateViewModel by viewModels { CreateTemplateViewModel.Factory }

    private val adapter: ParametersAdapter by lazy {
        ParametersAdapter { position ->
            if (adapter.itemCount > 1) {
                adapter.remove(position)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateTemplateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvParams.adapter = this.adapter
        binding.rvParams.layoutManager = LinearLayoutManager(requireContext())

        if (adapter.itemCount == 0) {
            addParameter()
        }

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
                playerName = "",
                startValue = 0,
                takeWhenCalc = false
            )
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}