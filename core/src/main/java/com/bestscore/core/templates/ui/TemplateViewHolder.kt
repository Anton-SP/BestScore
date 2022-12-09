package com.bestscore.core.templates.ui

import androidx.recyclerview.widget.RecyclerView
import com.bestscore.core.databinding.ItemTemplateBinding
import com.bestscore.core.templates.Template
import java.text.SimpleDateFormat
import java.util.*

class TemplateViewHolder(
    val binding: ItemTemplateBinding,
    private val onClickEdit: (Template) -> Unit,
    private val onClickDelete: (Template) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(template: Template) {
        with(binding) {
            tvTemplateName.text = template.name
            tvTemplateCreatedAt.text = formatDate(template.createdAt)

            this.root.setOnClickListener {
                if (this.root.scrollX != 0) {
                    this.root.scrollTo(0,0)
                }
            }

            btnEdit.setOnClickListener {
                onClickEdit.invoke(template)
            }

            btnDelete.setOnClickListener {
                onClickDelete.invoke(template)
            }
        }
    }

    private fun formatDate(date: Date): String {
        val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return df.format(date)
    }
}