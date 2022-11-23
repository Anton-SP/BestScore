package com.bestscore.featurestartscreen

import android.content.Context
import android.graphics.Canvas
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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.core.navigation.navigate
import com.bestscore.featurestartscreen.databinding.FragmentStartScreenBinding
import com.bestscore.featurestartscreen.di.StartScreenComponentViewModel
import com.bestscore.featurestartscreen.swipe.SwipeController
import com.bestscore.featurestartscreen.swipe.SwipeControllerActions
import com.bestscore.utils.makeToast
import javax.inject.Inject


class StartScreenFragment : Fragment(R.layout.fragment_start_screen) {

    @Inject
    internal lateinit var startScreenViewModelFactory: dagger.Lazy<StartScreenViewModel.Factory>

    private val startScreenViewModel: StartScreenViewModel by viewModels {
        startScreenViewModelFactory.get()
    }

    private val binding: FragmentStartScreenBinding by viewBinding()

    private val adapter: StartScreenAdapter by lazy {
        StartScreenAdapter(
            onClickEdit = {
                makeToast("Переход на редатирование")
            },
            onClickDelete = {
                makeToast("Заглушка на удаление")
            }
        )
    }

    //private val swipeController: SwipeController = SwipeController()
   // private val itemTouchHelper: ItemTouchHelper = ItemTouchHelper(swipeController)

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<StartScreenComponentViewModel>()
            .newStartScreenComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        collectFlow()

        startScreenViewModel.getTemplateList()

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

    private fun initRecycler() {
        binding.rvLastTemplates.apply {
            adapter = this@StartScreenFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())

            var swipeController = SwipeController(object : SwipeControllerActions() {
                override fun onRightClicked(position: Int) {
                    makeToast("Click click")
                }
            })
            val itemTouchHelper = ItemTouchHelper(swipeController)


            itemTouchHelper.attachToRecyclerView(this)

            this.addItemDecoration(object : ItemDecoration() {
                override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                    swipeController.onDraw(c)
                }
            })
        }

    }


    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            startScreenViewModel.getFlow().collect { state ->
                checkState(state)
            }
        }
    }

    private fun checkState(state: StartScreenViewModel.TemplatesListState) {
        when (state) {
            is StartScreenViewModel.TemplatesListState.Loading -> {
                with(binding) {
                    rvLastTemplates.isVisible = false
                    progress.isVisible = true
                }
            }
            is StartScreenViewModel.TemplatesListState.Success -> {
                with(binding) {
                    progress.isVisible = false
                    rvLastTemplates.isVisible = true
                }
                adapter.submitList(state.data)
            }
            is StartScreenViewModel.TemplatesListState.Error -> {
                with(binding) {
                    progress.isVisible = false
                    rvLastTemplates.isVisible = true
                }
                makeToast(state.message)
            }
        }
    }

}