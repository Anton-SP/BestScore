package com.bestscore.featurecreatetemplate

import androidx.recyclerview.widget.RecyclerView
import com.bestscore.core.Parameter
import com.bestscore.featurecreatetemplate.databinding.ItemParameterBinding

class ParameterViewHolder(
    private val binding: ItemParameterBinding,
    private val onClickDelete: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(parameter: Parameter) {
        binding.fabDeleteCard.setOnClickListener {
            onClickDelete.invoke(adapterPosition)
        }
    }
}