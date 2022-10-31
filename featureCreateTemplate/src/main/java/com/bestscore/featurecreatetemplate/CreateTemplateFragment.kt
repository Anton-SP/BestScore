package com.bestscore.featurecreatetemplate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bestscore.core.Parameter
import com.bestscore.featurecreatetemplate.databinding.FragmentCreateTemplateBinding

class CreateTemplateFragment : Fragment() {
    private var _binding: FragmentCreateTemplateBinding? = null
    private val binding get() = _binding!!

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

        binding.fabAddParameter.setOnClickListener {
            adapter.add(
                Parameter(
                    playerName = "name",
                    startValue = 0,
                    takeWhenCalc = false
                )
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}