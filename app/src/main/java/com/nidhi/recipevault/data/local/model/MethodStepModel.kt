package com.nidhi.recipevault.com.nidhi.recipevault.data.local.model

data class MethodStep(
    val recipeId: Int,        // Foreign key to Recipe
    val stepOrder: Int,       // Order of the step in the recipe
    val stepDescription: String // The actual description of the step
)
