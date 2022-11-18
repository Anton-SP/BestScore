package com.bestscore.featuretemplatelist

import androidx.recyclerview.widget.RecyclerView
import com.bestscore.core.templates.Template
import com.bestscore.featuretemplatelist.databinding.ItemTemplateBinding

class TemplatesListViewHolder(
    private val binding: ItemTemplateBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(template: Template) {
        with(binding) {
            tvTemplateName.text = template.name
            tvTemplateCreatedAt.text = template.createdAt.toString()
        }
    }
}