package com.nidhi.recipevault.data.local.model


import com.google.gson.annotations.SerializedName

data class RecipeVaultItemModel(
    @SerializedName("cookTimeMinutes")
    val cookTimeMinutes: Int,
    @SerializedName("cuisine")
    val cuisine: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("difficultyLevel")
    val difficultyLevel: String,
    @SerializedName("ingredients")
    val ingredients: List<IngredientModel>,
    @SerializedName("mealType")
    val mealType: String,
    @SerializedName("method")
    val method: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("prepTimeMinutes")
    val prepTimeMinutes: Int,
    @SerializedName("recipeId")
    val recipeId: Int,
    @SerializedName("servings")
    val servings: Int,
    @SerializedName("thumbnail")
    val thumbnail: String
)