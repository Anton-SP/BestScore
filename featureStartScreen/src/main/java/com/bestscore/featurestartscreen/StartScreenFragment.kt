package com.bestscore.featurestartscreen

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
import com.bestscore.core.navigation.navigate
import com.bestscore.core.swipe.SwipeController
import com.bestscore.core.templates.ui.BaseTemplateListFragment
import com.bestscore.core.templates.ui.TemplateDeleteState
import com.bestscore.core.templates.ui.TemplateListAdapter
import com.bestscore.core.templates.ui.TemplateListState
import com.bestscore.featurestartscreen.databinding.FragmentStartScreenBinding
import com.bestscore.featurestartscreen.di.StartScreenComponentViewModel
import com.bestscore.utils.makeToast
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartScreenFragment : BaseTemplateListFragment(R.layout.fragment_start_screen) {

    @Inject
    internal lateinit var startScreenViewModelFactory: dagger.Lazy<StartScreenViewModel.Factory>

    private val startScreenViewModel: StartScreenViewModel by viewModels {
        startScreenViewModelFactory.get()
    }

    private val binding: FragmentStartScreenBinding by viewBinding()

    private val adapter: TemplateListAdapter by lazy {
        TemplateListAdapter(
            onClickEdit = {
                makeToast("Переход на редатирование")
            },
            onClickDelete = { template ->
                startScreenViewModel.deleteTemplate(template)
            },
            onClickRoot = {
                navigate(R.id.action_startScreenFragment_to_playGameFragment, it)
            }
        )
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<StartScreenComponentViewModel>()
            .newStartScreenComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()

        collectListFlow()
        collectDeleteFlow()
        getTemplateList()
        initButtonsAndFabs()
    }

    private fun initButtonsAndFabs() {
        with(binding) {
            buttonCreateTemplate.setOnClickListener {
                navigate(R.id.action_startScreenFragment_to_createTemplateFragment)
            }
            buttonMyTemplates.setOnClickListener {
                navigate(R.id.action_startScreenFragment_to_templatesListFragment)
            }
            fabDice.setOnClickListener {
                navigate(R.id.action_startScreenFragment_to_diceDialogFragment)
            }
            fabTimer.setOnClickListener {
                navigate(R.id.action_startScreenFragment_to_timerDialogFragment)
            }
        }
    }

    private fun getTemplateList() {
        startScreenViewModel.getTemplateList()
    }

    private fun initRecycler() {
        binding.rvLastTemplates.apply {
            binding.swipeRefreshLayout.setOnRefreshListener {
                binding.swipeRefreshLayout.isRefreshing = false
            }

            adapter = this@StartScreenFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
            val swipeController = SwipeController(requireContext())
            val itemTouchHelper = ItemTouchHelper(swipeController)
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun collectListFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                startScreenViewModel.getListFlow().collect { state ->
                    checkListState(state)
                }
            }
        }
    }

    private fun collectDeleteFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                startScreenViewModel.getDeleteFlow().collect { state ->
                    checkDeleteState(state)
                }
            }
        }
    }

    private fun checkListState(state: TemplateListState) {
        when (state) {
            is TemplateListState.Loading -> {
                with(binding) {
                    rvLastTemplates.isVisible = false
                    progress.isVisible = true
                }
            }
            is TemplateListState.Success -> {
                with(binding) {
                    progress.isVisible = false
                    rvLastTemplates.isVisible = true
                }
                adapter.submitList(state.data)
            }
            is TemplateListState.Error -> {
                with(binding) {
                    progress.isVisible = false
                    rvLastTemplates.isVisible = true
                }
                makeToast(state.message)
            }
        }
    }

    private fun checkDeleteState(state: TemplateDeleteState) {
        when(state) {
            is TemplateDeleteState.Success -> {
                makeToast(text = getString(R.string.delete_template_successfully))
                startScreenViewModel.notifiedAboutDeleteTemplate()
                startScreenViewModel.getTemplateList()
            }

            is TemplateDeleteState.Error -> {
                makeToast(text = state.message)
                startScreenViewModel.notifiedAboutDeleteTemplate()
            }
            is TemplateDeleteState.Nothing -> {}
        }
    }

}