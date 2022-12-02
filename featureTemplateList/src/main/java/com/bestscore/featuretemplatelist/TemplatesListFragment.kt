package com.bestscore.featuretemplatelist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.core.swipe.SwipeController
import com.bestscore.featuretemplatelist.databinding.FragmentTemplatesListBinding
import com.bestscore.featuretemplatelist.di.TemplatesListComponentViewModel
import com.bestscore.utils.makeToast
import javax.inject.Inject

class TemplatesListFragment : Fragment(R.layout.fragment_templates_list) {

    @Inject
    internal lateinit var templatesListViewModelFactory: dagger.Lazy<TemplatesListViewModel.Factory>

    private val templatesListViewModel: TemplatesListViewModel by viewModels {
        templatesListViewModelFactory.get()
    }

    private val binding: FragmentTemplatesListBinding by viewBinding()

    private val adapter: TemplatesListAdapter by lazy {
        TemplatesListAdapter(
            onClickEdit = {
                makeToast("Переход на редатирование")
            },
            onClickDelete = {
                makeToast("Заглушка на удаление")
            }
        )
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<TemplatesListComponentViewModel>()
            .newTemplateListComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        collectFlow()

        templatesListViewModel.getTemplateList()
    }

    private fun initRecycler() {
        binding.rvTemplates.apply {

            binding.swipeRefreshLayout.setOnRefreshListener {
                binding.swipeRefreshLayout.isRefreshing = false
            }
            adapter = this@TemplatesListFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
            val swipeController = SwipeController(requireContext())
            val itemTouchHelper = ItemTouchHelper(swipeController)
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            templatesListViewModel.getFlow().collect { state ->
                checkState(state)
            }
        }
    }

    private fun checkState(state: TemplatesListViewModel.TemplatesListState) {
        when (state) {
            is TemplatesListViewModel.TemplatesListState.Loading -> {
                with(binding) {
                    rvTemplates.isVisible = false
                    progress.isVisible = true
                }
            }
            is TemplatesListViewModel.TemplatesListState.Success -> {
                with(binding) {
                    progress.isVisible = false
                    rvTemplates.isVisible = true
                }
                adapter.submitList(state.data)
            }
            is TemplatesListViewModel.TemplatesListState.Error -> {
                with(binding) {
                    progress.isVisible = false
                    rvTemplates.isVisible = true
                }
                makeToast(state.message)
            }
        }
    }
}