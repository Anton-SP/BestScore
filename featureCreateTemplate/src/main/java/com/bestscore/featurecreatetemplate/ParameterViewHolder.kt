package com.bestscore.featurecreatetemplate

import android.text.Editable
import android.text.TextWatcher
import android.widget.CompoundButton
import android.widget.EditText
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

            edPlayerName.setText(parameter.playerName)
            edPlayerName.addTextChangedListener {
                parameter.playerName = it.toString()
            }

            edStartScore.setText(parameter.startValue.toString())
            edStartScore.addTextChangedListener {
                parameter.startValue = it.toString().toInt()
            }

            swTakeWhenCalc.isChecked = parameter.takeWhenCalc
            swTakeWhenCalc.setOnCheckedChangeListener { _, checked -> parameter.takeWhenCalc = checked }

        }
    }
}