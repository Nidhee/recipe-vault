package com.nidhi.recipevault.com.nidhi.recipevault.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.nidhi.recipevault.R
import com.nidhi.recipevault.com.nidhi.recipevault.ui.adapter.RecipePagerAdapter
import com.nidhi.recipevault.com.nidhi.recipevault.utils.LogUtils
import com.nidhi.recipevault.com.nidhi.recipevault.utils.getDrawableIdByName
import com.nidhi.recipevault.databinding.RecipeDetailBinding
import com.nidhi.recipevault.domain.model.Recipe

class RecipeDetailFragment : Fragment() {
    private var _binding: RecipeDetailBinding? = null
    private val binding get() = _binding!!
    var recipe : Recipe? = null
    private var recipePagerAdapter : RecipePagerAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipeDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(LogUtils.getTag(this::class.java), "RecipeDetailFragment class onViewCreated called")
        _binding = RecipeDetailBinding.bind(view)


        // Enable Back Button
        val toolbar : Toolbar = binding.recipeDetailToolBar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        // Handle Back Button Click
        binding.recipeDetailToolBar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        recipe = arguments?.getParcelable("recipe")
        recipe?.let {
            setRecipeData(it)
        }
        initViews()
    }
    private fun initViews(){
        recipePagerAdapter = RecipePagerAdapter(this, recipe!!)
        binding.viewPager.adapter = recipePagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "Ingredients" else "Method"

        }.attach()
    }
    private fun setRecipeData(recipe: Recipe) {
        Glide.with(binding.recipeImage.context)
            .load(context?.getDrawableIdByName(recipe.thumbnail))
            .placeholder(R.drawable.ic_default_thumbnail)
            .into(binding.recipeImage)
        binding.recipeDetailCollapsingToolbar.title = recipe.name
        binding.recipeDescription.text = recipe.description
        binding.cookTime.text =  getString(R.string.cook_time, recipe.cookTimeMinutes.toString())
        binding.prepTime.text =  getString(R.string.prep_time, recipe.prepTimeMinutes.toString())
        binding.difficulty.text = recipe.difficultyLevel.toString()
        binding.cuisine.text = recipe.cuisine.toString()
        binding.mealType.text = recipe.mealType.toString()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(LogUtils.getTag(this::class.java), "RecipeDetailFragment class onDestroyView called")
        // show main activity tool bar
        _binding = null
    }
}