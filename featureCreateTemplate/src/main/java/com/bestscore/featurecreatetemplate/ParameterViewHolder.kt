package com.bestscore.featurecreatetemplate

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.bestscore.core.templates.Parameter
import com.bestscore.featurecreatetemplate.databinding.ItemParameterBinding

class ParameterViewHolder(
    private val binding: ItemParameterBinding,
    private val onClickDelete: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(parameter: Parameter) {
        with(binding) {
            fabDeleteCard.setOnClickListener {
                onClickDelete.invoke(adapterPosition)
            }

            edPlayerName.setText(parameter.parameterName)
            edPlayerName.addTextChangedListener {
                parameter.parameterName = it.toString()
            }

            edStartScore.setText(parameter.startValue.toString())
            edStartScore.addTextChangedListener {
                if(it.toString().isNotBlank()) {
                    parameter.startValue = it.toString().toInt()
                    parameter.inGameValues[0] = it.toString().toInt()
                }
            }

            swTakeWhenCalc.isChecked = parameter.takeWhenCalc
            swTakeWhenCalc.setOnCheckedChangeListener { _, checked -> parameter.takeWhenCalc = checked }

        }
    }
}