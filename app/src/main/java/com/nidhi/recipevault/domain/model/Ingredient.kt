package com.nidhi.recipevault.com.nidhi.recipevault.domain.model

data class Ingredient(
    val ingredientId: Int = 0,
    val recipeId: Int,
    val item: String,
    val qty: Double?,
    val maxQty: Double?,
    val unit: RecipeUnitDomain?
)