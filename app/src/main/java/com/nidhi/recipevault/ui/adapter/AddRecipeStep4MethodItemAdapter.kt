// AddRecipeStep4MethodItemAdapter.kt

package com.nidhi.recipevault.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nidhi.recipevault.R
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.MethodStep
import com.nidhi.recipevault.databinding.AddRecipeStep4MethodItemBinding

class AddRecipeStep4MethodItemAdapter(
    private val onItemRemove: (Int) -> Unit,
    private val onDescriptionChanged: (Int, String) -> Unit
) : ListAdapter<MethodStep, AddRecipeStep4MethodItemAdapter.MethodStepViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MethodStepViewHolder {
        val binding = AddRecipeStep4MethodItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        // Force LTR direction for quantity field
        binding.methodStepItemDescription.textDirection = android.view.View.TEXT_DIRECTION_LTR
        binding.methodStepItemDescription.layoutDirection = android.view.View.LAYOUT_DIRECTION_LTR
        return MethodStepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MethodStepViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class MethodStepViewHolder(
        private val binding: AddRecipeStep4MethodItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private var descWatcher: TextWatcher? = null

        fun bind(methodStep: MethodStep, position: Int) {

            // Set dynamic hint with step order shown (position starts at 0, steps usually start at 1)
            val context = binding.methodStepItemDescriptionLayout.context
            val hint = context.getString(R.string.method_add_step_hint, position + 1)
            binding.methodStepItemDescriptionLayout.hint = hint


            descWatcher?.let { binding.methodStepItemDescription.removeTextChangedListener(it) }
            if (binding.methodStepItemDescription.text?.toString() != methodStep.stepDescription) {
                binding.methodStepItemDescription.setText(methodStep.stepDescription)
            }
            // Re-enforce LTR and place cursor at end
            binding.methodStepItemDescription.textDirection = android.view.View.TEXT_DIRECTION_LTR
            binding.methodStepItemDescription.layoutDirection = android.view.View.LAYOUT_DIRECTION_LTR
            binding.methodStepItemDescription.setSelection(binding.methodStepItemDescription.text?.length ?: 0)

            descWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val currentPosition = adapterPosition
                    if (currentPosition != RecyclerView.NO_POSITION && currentPosition == position) {
                        onDescriptionChanged(currentPosition, s?.toString() ?: "")
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            }
            binding.methodStepItemDescription.addTextChangedListener(descWatcher)

            binding.methodStepRemoveBtn.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onItemRemove(pos)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MethodStep>() {
        override fun areItemsTheSame(old: MethodStep, new: MethodStep) = old.stepId == new.stepId
        override fun areContentsTheSame(old: MethodStep, new: MethodStep) = old == new
    }
}