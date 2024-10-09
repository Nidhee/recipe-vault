package com.nidhi.recipevault.com.nidhi.recipevault.domain.model

data class MethodStep(
    val stepId: Int = 0,
    val recipeId: Int,
    val stepOrder: Int,
    val stepDescription: String)

