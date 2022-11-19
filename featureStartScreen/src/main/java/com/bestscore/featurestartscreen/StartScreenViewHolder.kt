package com.bestscore.featurestartscreen

import androidx.recyclerview.widget.RecyclerView
import com.bestscore.core.templates.Template
import com.bestscore.featurestartscreen.databinding.ItemLastTemplateBinding
import com.bestscore.utils.formatDate

class StartScreenViewHolder(
    private val binding: ItemLastTemplateBinding,
    private val onClickEdit: (Template) -> Unit,
    private val onClickDelete: (Template) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(template: Template) {
        with(binding) {
            tvTemplateName.text = template.name
            tvTemplateCreatedAt.text = formatDate(template.createdAt)

            btnEdit.setOnClickListener {
                onClickEdit.invoke(template)
            }

            btnDelete.setOnClickListener {
                onClickDelete.invoke(template)
            }
        }
    }
}