package com.bestscore.featureplaygame

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.core.navigation.navigationData
import com.bestscore.core.templates.Template
import com.bestscore.featureplaygame.databinding.FragmentPlayGameBinding
import com.bestscore.utils.makeToast
import kotlinx.coroutines.launch

class PlayGameFragment : Fragment(R.layout.fragment_play_game) {

    private val binding: FragmentPlayGameBinding by viewBinding()

    private val playGameViewModel: PlayGameViewModel by viewModels()

    private var adapter: PlayGameAdapter? = null

    private var template: Template? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTemplateData()
        initViews()
        initViewModel()
    }

    private fun getTemplateData() {
        template = navigationData as Template?
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                playGameViewModel.uiState.collect { uiState ->
                    renderData(uiState)
                }
            }
        }
    }

    private fun renderData(uiState: PlayGameUiState) {
        when (uiState) {
            is PlayGameUiState.Error -> {
                makeToast(uiState.err.message.toString())
            }
            PlayGameUiState.Started -> {
                playGameViewModel.setData(template)
            }
            is PlayGameUiState.Success -> {
                adapter?.submitList(uiState.data)
                if (uiState.calculatedValue == null) {
                    binding.tvCalculatedScoreLabel.visibility = View.INVISIBLE
                } else {
                    binding.tvCalculatedScoreLabel.visibility = View.VISIBLE
                    binding.tvCalculatedScore.text = uiState.calculatedValue.toString()
                }
            }
        }
    }

    private fun initViews() {
        initAdapter()
        binding.tvTemplateName.text = template?.name
    }

    private fun initAdapter() {
        adapter = PlayGameAdapter(::onApplyButtonClicked)
        if (template != null) {
            adapter?.submitList(template?.parameters)
        }
        binding.rvGameParameters.adapter = adapter
        binding.rvGameParameters.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun onApplyButtonClicked(index: Int, changeValue: Int) {
        val request = ChangeValueRequestModel(
            data = adapter!!.currentList,
            index = index,
            valueOfChange = changeValue
        )
        viewLifecycleOwner.lifecycleScope.launch {
            playGameViewModel.changeParameterValue(request)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        template = null
    }
}