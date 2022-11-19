package com.bestscore.featurestartscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bestscore.core.templates.Template
import androidx.recyclerview.widget.DiffUtil
import com.bestscore.featurestartscreen.databinding.ItemLastTemplateBinding


class StartScreenAdapter(
    private val onClickEdit: (Template) -> Unit,
    private val onClickDelete: (Template) -> Unit
) : ListAdapter<Template, StartScreenViewHolder>(DiffUtilsItemCallbackImpl()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartScreenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_last_template, parent, false)

        return StartScreenViewHolder(
            binding = ItemLastTemplateBinding.bind(view),
            onClickEdit = onClickEdit,
            onClickDelete = onClickDelete
        )
    }

    override fun onBindViewHolder(holder: StartScreenViewHolder, position: Int) {
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