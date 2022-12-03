package com.bestscore.featureplaygame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bestscore.core.templates.Parameter
import com.bestscore.featureplaygame.databinding.ItemGameParameterBinding

internal class PlayGameAdapter(
    private val onApplyClickListener: () -> Unit
) : ListAdapter<Parameter, PlayGameAdapter.ParameterViewHolder>(ParameterItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParameterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ParameterViewHolder(
            binding = ItemGameParameterBinding.inflate(layoutInflater),
        )
    }

    override fun onBindViewHolder(holder: ParameterViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ParameterViewHolder(
        private val binding: ItemGameParameterBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(parameter: Parameter) {
            with(binding) {
                tvItemParameterName.text = parameter.parameterName
                tvItemScore.text = parameter.inGameValues.last().toString()
                btnItemApplyScoreChange.setOnClickListener {
                    onApplyClickListener
                }
            }
        }
    }

    class ParameterItemCallback : DiffUtil.ItemCallback<Parameter>() {
        override fun areItemsTheSame(oldItem: Parameter, newItem: Parameter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Parameter, newItem: Parameter): Boolean {
            return oldItem == newItem
        }

    }
}