package com.bestscore.featuretemplatelist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.core.swipe.SwipeController
import com.bestscore.core.templates.ui.BaseTemplateListFragment
import com.bestscore.core.templates.ui.TemplateDeleteState
import com.bestscore.core.templates.ui.TemplateListState
import com.bestscore.featuretemplatelist.databinding.FragmentTemplatesListBinding
import com.bestscore.featuretemplatelist.di.TemplatesListComponentViewModel
import com.bestscore.utils.makeToast
import kotlinx.coroutines.launch
import javax.inject.Inject

class TemplatesListFragment : BaseTemplateListFragment(R.layout.fragment_templates_list) {

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
            onClickDelete = { template ->
                templatesListViewModel.deleteTemplate(template)
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
        collectListFlow()
        collectDeleteFlow()

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

    private fun collectListFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                templatesListViewModel.getListFlow().collect { state ->
                    checkListState(state)
                }
            }
        }
    }

    private fun collectDeleteFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                templatesListViewModel.getDeleteFlow().collect { state ->
                    checkDeleteState(state)
                }
            }
        }
    }

    private fun checkListState(state: TemplateListState) {
        when (state) {
            is TemplateListState.Loading -> {
                with(binding) {
                    rvTemplates.isVisible = false
                    progress.isVisible = true
                }
            }
            is TemplateListState.Success -> {
                with(binding) {
                    progress.isVisible = false
                    rvTemplates.isVisible = true
                }
                adapter.submitList(state.data)
            }
            is TemplateListState.Error -> {
                with(binding) {
                    progress.isVisible = false
                    rvTemplates.isVisible = true
                }
                makeToast(state.message)
            }
        }
    }

    private fun checkDeleteState(state: TemplateDeleteState) {
        when(state) {
            is TemplateDeleteState.Success -> {
                makeToast(text = getString(R.string.delete_template_successfully))
                templatesListViewModel.notifiedAboutDeleteTemplate()
                templatesListViewModel.getTemplateList()
            }

            is TemplateDeleteState.Error -> {
                makeToast(text = state.message)
                templatesListViewModel.notifiedAboutDeleteTemplate()
            }
            is TemplateDeleteState.Nothing -> {}
        }
    }
}