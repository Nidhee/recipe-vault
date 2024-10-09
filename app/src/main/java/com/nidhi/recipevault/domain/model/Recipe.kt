package com.nidhi.recipevault.domain.model

import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.CuisineDomain
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.DifficultyLevelDomain
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.Ingredient
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.MealTypeDomain
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.MethodStep


data class Recipe(
    val recipeId: Int,
    val name: String,
    val description: String?,
    val prepTimeMinutes: Int?,
    val cookTimeMinutes: Int?,
    val servings: Int?,
    val difficultyLevel: DifficultyLevelDomain?,
    val mealType: MealTypeDomain?,
    val cuisine: CuisineDomain?,
    val thumbnail: String?,
    val ingredients: List<Ingredient>,
    val methodSteps: List<MethodStep>
)