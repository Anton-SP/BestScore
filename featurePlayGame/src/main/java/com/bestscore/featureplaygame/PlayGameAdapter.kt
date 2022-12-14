package com.bestscore.featureplaygame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bestscore.core.templates.Parameter
import com.bestscore.featureplaygame.databinding.ItemGameParameterBinding
import com.bestscore.utils.makeSnackbar

internal class PlayGameAdapter(
    private val onApplyClickListener: (Int, Int) -> Unit
) : ListAdapter<Parameter, PlayGameAdapter.ParameterViewHolder>(ParameterItemCallback()) {

    companion object {
        const val EDIT_TEXT_IS_BLANK_ERR = "Заполните поле \"изменить\""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParameterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_game_parameter, parent, false)
        return ParameterViewHolder(
            binding = ItemGameParameterBinding.bind(view)
        )
    }

    override fun onBindViewHolder(holder: ParameterViewHolder, position: Int) {
        holder.bind(parameter = currentList[position], index = position)
    }

    inner class ParameterViewHolder(
        private val binding: ItemGameParameterBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(parameter: Parameter, index: Int) {
            with(binding) {
                tvItemParameterName.text = parameter.parameterName
                tvItemScore.text = parameter.inGameValues.last().toString()
                btnItemApplyScoreChange.setOnClickListener {
                    val value = etItemScoreChange.text
                    if (value.isNotBlank()) {
                        onApplyClickListener(index, value.toString().toInt())
                        tvItemScore.text = parameter.inGameValues.last().toString()
                        etItemScoreChange.setText("")
                    } else {
                        binding.root.makeSnackbar(EDIT_TEXT_IS_BLANK_ERR)
                    }
                }
            }
        }
    }

    class ParameterItemCallback : DiffUtil.ItemCallback<Parameter>() {
        override fun areItemsTheSame(oldItem: Parameter, newItem: Parameter): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Parameter, newItem: Parameter): Boolean {
            return oldItem == newItem
        }

    }
}