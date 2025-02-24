package com.nidhi.recipevault.com.nidhi.recipevault.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nidhi.recipevault.R
import com.nidhi.recipevault.databinding.RecipeItemBinding
import com.nidhi.recipevault.domain.model.Recipe
import javax.inject.Inject

class RecipeAdapter constructor(
    private val onItemClick: (Recipe)  -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var recipeList: List<Recipe> = listOf()

    inner class RecipeViewHolder(private val binding: RecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            // Load image
            Glide.with(binding.itemThumbnail.context)
                .load(getDrawableIdByName(binding.itemThumbnail.context, recipe.thumbnail))
                .placeholder(R.drawable.ic_default_thumbnail)
                .into(binding.itemThumbnail)

            // Set title
            binding.itemTitle.text = recipe.name
            // cooking time
            binding.itemCookTime.text = formatTotalTime(recipe.prepTimeMinutes, recipe.cookTimeMinutes)
            itemView.setOnClickListener { onItemClick(recipe) }
        }

        private fun getDrawableIdByName(context: Context, name: String?): Int {
            return name?.let {
                context.resources.getIdentifier(it, "drawable", context.packageName)
            } ?: R.drawable.ic_default_thumbnail
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipeList[position])
    }

    override fun getItemCount(): Int = recipeList.size

    // Method to update the list of recipes
    fun setRecipes(newRecipes: List<Recipe>) {
        val diffCallback = RecipeDiffCallback(recipeList, newRecipes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        recipeList = newRecipes
        diffResult.dispatchUpdatesTo(this)
    }
    private fun formatTotalTime(prepTimeMinutes: Int?, cookTimeMinutes: Int?): String {
        val totalMinutes = (prepTimeMinutes ?: 0) + (cookTimeMinutes ?: 0)
        return if (totalMinutes > 0) "$totalMinutes min${if (totalMinutes > 1) "s" else ""}" else "N/A"
    }
}
class RecipeDiffCallback(
    private val oldList: List<Recipe>,
    private val newList: List<Recipe>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].recipeId == newList[newItemPosition].recipeId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}