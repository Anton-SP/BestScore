package com.bestscore.core.templates.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bestscore.core.R
import com.bestscore.core.databinding.ItemTemplateBinding
import com.bestscore.core.templates.Template

class TemplateListAdapter(
    private val onClickEdit: (Template) -> Unit,
    private val onClickDelete: (Template) -> Unit,
    private val onClickRoot: (Template) -> Unit
) : ListAdapter<Template, TemplateViewHolder>(DiffUtilsItemCallbackImpl()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_template, parent, false)

        return TemplateViewHolder(
            binding = ItemTemplateBinding.bind(view),
            onClickEdit = onClickEdit,
            onClickDelete = onClickDelete,
            onClickRoot = onClickRoot
        )
    }

    override fun onBindViewHolder(holder: TemplateViewHolder, position: Int) {
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