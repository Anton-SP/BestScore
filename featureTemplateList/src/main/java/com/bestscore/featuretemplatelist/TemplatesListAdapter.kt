package com.bestscore.featuretemplatelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bestscore.core.templates.Template
import com.bestscore.featuretemplatelist.databinding.ItemTemplateBinding

class TemplatesListAdapter()
    : ListAdapter<Template, TemplatesListViewHolder>(DiffUtilsItemCallbackImpl()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplatesListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_template, parent, false)

        return TemplatesListViewHolder(
            binding = ItemTemplateBinding.bind(view)
        )
    }

    override fun onBindViewHolder(holder: TemplatesListViewHolder, position: Int) {
        holder.bind(template = currentList[position])
    }
}

private class DiffUtilsItemCallbackImpl : DiffUtil.ItemCallback<Template>() {
    override fun areItemsTheSame(oldItem: Template, newItem: Template): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Template, newItem: Template): Boolean {
        return oldItem == newItem
    }
}