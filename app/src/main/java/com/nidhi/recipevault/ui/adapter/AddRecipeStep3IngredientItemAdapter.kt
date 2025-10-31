package com.nidhi.recipevault.com.nidhi.recipevault.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.Ingredient
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.RecipeUnitDomain
import com.nidhi.recipevault.databinding.AddRecipeStep3IngredientItemBinding

class AddRecipeStep3IngredientItemAdapter(
    private val onItemRemove: (Int) -> Unit,
    private val onUnitSelected: (Int, RecipeUnitDomain) -> Unit,
    private val onItemNameChanged: (Int, String) -> Unit,
    private val onQuantityChanged: (Int, String) -> Unit
) : ListAdapter<Ingredient, AddRecipeStep3IngredientItemAdapter.IngredientViewHolder>(IngredientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = AddRecipeStep3IngredientItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        // Force LTR direction for quantity field
        binding.ingredientQty.textDirection = android.view.View.TEXT_DIRECTION_LTR
        binding.ingredientQty.layoutDirection = android.view.View.LAYOUT_DIRECTION_LTR
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class IngredientViewHolder(private val binding: AddRecipeStep3IngredientItemBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        private var itemNameWatcher: android.text.TextWatcher? = null
        private var qtyWatcher: android.text.TextWatcher? = null

        fun bind(ingredient: Ingredient, position : Int) {
            binding.apply {

                // Remove existing watchers before adding new ones
                itemNameWatcher?.let { ingredientItemName.removeTextChangedListener(it) }
                qtyWatcher?.let { ingredientQty.removeTextChangedListener(it) }

                // Set text without triggering watchers
                ingredientItemName.setText(ingredient.item)
                // Move cursor to end after setting text
                ingredientItemName.setSelection(ingredientItemName.text?.length ?:0)
                // Create and add new watcher
                itemNameWatcher = object : android.text.TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val currentPosition = adapterPosition
                        if (currentPosition != RecyclerView.NO_POSITION && currentPosition == position) {
                            onItemNameChanged(currentPosition, s?.toString() ?: "")
                        }
                    }
                    override fun afterTextChanged(s: android.text.Editable?) {}
                }
                ingredientItemName.addTextChangedListener(itemNameWatcher)


                ingredientQty.setText(formatQuantity(ingredient.qty))
                // Force LTR text direction programmatically (re-enforce in case it was reset)
                ingredientQty.textDirection = android.view.View.TEXT_DIRECTION_LTR
                ingredientQty.layoutDirection = android.view.View.LAYOUT_DIRECTION_LTR
                ingredientQty.setSelection(ingredientQty.text?.length ?: 0)
                // Create and add new watcher
                qtyWatcher = object : android.text.TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val currentPosition = adapterPosition
                        if (currentPosition != RecyclerView.NO_POSITION && currentPosition == position) {
                            onQuantityChanged(currentPosition, s?.toString() ?: "")
                        }
                    }
                    override fun afterTextChanged(s: android.text.Editable?) {}
                }
                ingredientQty.addTextChangedListener(qtyWatcher)

                ingredientUnit.setText(ingredient.unit?.name?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "Unit")
                ingredientUnit.setOnClickListener {
                    val currentPosition = adapterPosition
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        showUnitDialog(currentPosition)
                    }
                }

                ingredientRemoveBtn.setOnClickListener {
                    val currentPosition = adapterPosition
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        onItemRemove(currentPosition)
                    }
                }
            }
        }

        private fun showUnitDialog(position: Int) {
            val currentUnit = getItem(position).unit
            val units = RecipeUnitDomain.entries
            val unitNames = units.map { it.name.lowercase().replaceFirstChar { it.uppercase() } }

            androidx.appcompat.app.AlertDialog.Builder(binding.root.context)
                .setTitle("Select Unit")
                .setItems(unitNames.toTypedArray()) { _, which ->
                    val selectedUnit = units[which]
                    onUnitSelected(position, selectedUnit)
                }
                .show()
        }
        private fun formatQuantity(value: Double?): String {
            return if (value == null) {
                ""
            } else if (value % 1 == 0.0) {
                value.toInt().toString()
            } else {
                value.toString()
            }
        }
    }

    class IngredientDiffCallback : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem.ingredientId == newItem.ingredientId
        }

        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem == newItem
        }
    }
}