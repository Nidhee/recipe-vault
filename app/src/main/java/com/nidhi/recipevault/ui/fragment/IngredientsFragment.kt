package com.nidhi.recipevault.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nidhi.recipevault.R
import com.nidhi.recipevault.domain.model.Ingredient
import com.nidhi.recipevault.databinding.RecipeDetailIngredientsBinding
import com.nidhi.recipevault.domain.model.Recipe

class IngredientsFragment : Fragment() {

    private var recipe: Recipe? = null
    private var _binding: RecipeDetailIngredientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipeDetailIngredientsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = RecipeDetailIngredientsBinding.bind(view)
        arguments?.let {
            recipe = it.getParcelable(ARG_RECIPE) // Retrieve recipe data
        }
        initViews()
    }

    private fun initViews() {
        recipe?.ingredients?.let { setIngredientsData(it) }
        binding.recipeDetailIngredientsTextView.post {
            binding.recipeDetailIngredientsTextView.requestLayout()
        }
    }

    private fun setIngredientsData(ingredients: List<Ingredient>) {
        val ingredientsText = ingredients.joinToString("\n\n") { item ->
            if (item.maxQty != 0.0) {
                getString(
                    R.string.ingredient_format_with_max,
                    formatQuantity(item.qty),
                    formatQuantity(item.maxQty),
                    item.unit.toString().lowercase().replaceFirstChar { it.uppercase() },
                    item.item
                )
            } else {
                getString(
                    R.string.ingredient_format_without_max,
                    formatQuantity(item.qty),
                    item.unit.toString().lowercase().replaceFirstChar { it.uppercase() },
                    item.item
                )
            }
        }
        binding.recipeDetailIngredientsTextView.text = ingredientsText
    }

    private fun formatQuantity(value: Double?): String {
        return if (value == null) "" else if (value % 1 == 0.0) value.toInt().toString() else {
            value.toString()
        }
    }

    companion object {
        private const val ARG_RECIPE = "recipe"
        fun newInstance(recipe: Recipe) = IngredientsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_RECIPE, recipe)
            }
        }
    }
}