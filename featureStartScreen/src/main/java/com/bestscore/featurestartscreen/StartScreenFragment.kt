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

            binding.swipeRefreshLayout.setOnRefreshListener {
                binding.swipeRefreshLayout.isRefreshing = false

            }
            adapter = this@StartScreenFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())

            setItemTouchHelper()
        }

    }

    private fun setItemTouchHelper() {

        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            //limit to swipe same as buttons
            private val limitScrollX = dpToPx(96f, this@StartScreenFragment.requireContext())
            /* private val limitScrollX =
                 this@StartScreenFragment.context?.let {
                     dpToPx(
                         48f,
                         it
                     )
                 } */

            private var currentScrollX = 0
            private var currentScrollXWhenInActive = 0
            private var initXWhenInActive = 0f
            private var firstInActive = false


            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = 0
                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return Integer.MAX_VALUE.toFloat()
            }

            override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
                return Integer.MAX_VALUE.toFloat()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    if (dX == 0f) {
                        currentScrollX = viewHolder.itemView.scrollX
                        firstInActive = true
                    }

                    if (isCurrentlyActive) {
                        //swipe with finger

                        var scrollOffset = currentScrollX + (-dX).toInt()
                        if (scrollOffset > limitScrollX) {
                            scrollOffset = limitScrollX
                        } else if (scrollOffset < 0) {
                            scrollOffset = 0
                        }
                        viewHolder.itemView.scrollTo(scrollOffset, 0)
                    } else {

                        //swipe with auto animation
                        if (firstInActive) {
                            firstInActive = false
                            currentScrollXWhenInActive = viewHolder.itemView.scrollX
                            initXWhenInActive = dX
                        }

                        if (viewHolder.itemView.scrollX < limitScrollX) {
                            viewHolder.itemView.scrollTo(
                                (currentScrollXWhenInActive * dX / initXWhenInActive).toInt(),
                                0
                            )
                        }

                    }

                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)

                if (viewHolder.itemView.scrollX > limitScrollX) {
                    viewHolder.itemView.scrollTo(limitScrollX,0)
                }
                else if (viewHolder.itemView.scrollX < 0) {
                    viewHolder.itemView.scrollTo(0,0)
                }


            }

        }).apply {
            attachToRecyclerView(binding.rvLastTemplates)
        }

    }

    private fun dpToPx(dpValue: Float, context: Context): Int {
        return (dpValue * context.resources.displayMetrics.density).toInt()
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