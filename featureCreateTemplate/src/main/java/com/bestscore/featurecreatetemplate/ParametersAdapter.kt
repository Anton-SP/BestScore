package com.bestscore.featurecreatetemplate

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.bestscore.core.templates.Parameter
import com.bestscore.featurecreatetemplate.databinding.ItemParameterBinding

class ParametersAdapter(
    private val onClickDelete: (Int) -> Unit
) : RecyclerView.Adapter<ParametersAdapter.ParameterViewHolder>() {
    private val parameters = ArrayList<Parameter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParameterViewHolder {
        val binding = ItemParameterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val holder = ParameterViewHolder(binding = binding)
        holder.setListeners()
        return holder
    }

    override fun onBindViewHolder(holder: ParameterViewHolder, position: Int) {
        holder.updatePositions(position = position)
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

    fun updateParameters(newParameters: List<Parameter>) {
        parameters.clear()
        parameters.addAll(newParameters)
        notifyDataSetChanged()
    }

    inner class ParameterViewHolder(
        private val binding: ItemParameterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setListeners() {
            with(binding) {
                edParameterName.addTextChangedListener(edParameterNameListener)
                edStartValue.addTextChangedListener(edStartValueListener)
                swTakeWhenCalc.setOnCheckedChangeListener(swTakeWhenCalcListener)
            }
        }

        fun updatePositions(position: Int) {
            edParameterNameListener.itemPosition = position
            edStartValueListener.itemPosition = position
            swTakeWhenCalcListener.itemPosition = position
        }

        fun bind(parameter: Parameter) {
            with(binding) {
                btnDelete.setOnClickListener {
                    onClickDelete.invoke(bindingAdapterPosition)
                }
                edParameterName.setText(parameter.parameterName)
                edStartValue.setText(parameter.startValue.toString())
                swTakeWhenCalc.isChecked = parameter.takeWhenCalc
            }
        }

        private val edParameterNameListener = object : RecyclerTextWatcher {
            override var itemPosition: Int = -1

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                parameters[itemPosition].parameterName = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
        }

        private val edStartValueListener = object : RecyclerTextWatcher {
            override var itemPosition: Int = -1

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotBlank()) {
                    parameters[itemPosition].startValue = s.toString().toInt()
                    parameters[itemPosition].inGameValues[0] = s.toString().toInt()
                }
            }

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
        }

        private val swTakeWhenCalcListener = object : RecyclerOnCheckedChangeListener {
            override var itemPosition: Int = -1

            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                parameters[itemPosition].takeWhenCalc = isChecked
            }
        }
    }
}
