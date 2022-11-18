package com.bestscore.featuretemplatelist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bestscore.featuretemplatelist.databinding.FragmentTemplatesListBinding
import javax.inject.Inject

class TemplatesListFragment : Fragment(R.layout.fragment_templates_list) {

    @Inject
    internal lateinit var templateListViewModel: dagger.Lazy<TemplateListViewModel.Factory>

    private val binding: FragmentTemplatesListBinding by viewBinding()

    private val adapter: TemplatesListAdapter by lazy { TemplatesListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}