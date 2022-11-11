package com.bestscore.featurecreatetemplate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bestscore.core.templates.Parameter
import com.bestscore.featurecreatetemplate.databinding.ItemParameterBinding

class ParametersAdapter(
    private val onClickDelete: (Int) -> Unit
) : RecyclerView.Adapter<ParameterViewHolder>() {
    private val parameters = ArrayList<Parameter> ()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParameterViewHolder {
        val binding = ItemParameterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ParameterViewHolder(binding = binding, onClickDelete=onClickDelete)
    }

    override fun onBindViewHolder(holder: ParameterViewHolder, position: Int) {
        holder.bind(parameter = parameters[position])
    }

    override fun getItemCount(): Int {
        return parameters.size
    }

    fun add(parameter: Parameter) {
        parameters.add(parameter)
        notifyItemInserted(itemCount - 1)
    }

    fun remove(position: Int) {
        parameters.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getCurrentList(): List<Parameter> = parameters
}