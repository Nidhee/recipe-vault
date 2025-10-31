package com.nidhi.recipevault.data.local.database.converters

import androidx.compose.ui.text.toUpperCase
import androidx.room.TypeConverters
import com.nidhi.recipevault.data.local.database.Cuisine
import com.nidhi.recipevault.data.local.database.DifficultyLevel
import com.nidhi.recipevault.data.local.database.MealType
import com.nidhi.recipevault.data.local.database.RecipeUnit

class RecipeVaultTypeConverters {
    @TypeConverters
    fun fromDifficultyLevel(value : DifficultyLevel) : String = value.name

    @TypeConverters
    fun toDifficultyLevel(value : String) : DifficultyLevel = DifficultyLevel.valueOf(value.toUpperCase())

    @TypeConverters
    fun fromMealType(value : MealType) : String = value.name

    @TypeConverters
    fun toMealType(value : String) : MealType = MealType.valueOf(value.toUpperCase())

    @TypeConverters
    fun fromCuisine(value : Cuisine) : String = value.name

    @TypeConverters
    fun toCuisine(value : String) : Cuisine = Cuisine.valueOf(value.toUpperCase())

    @TypeConverters
    fun fromRecipeUnit(value : RecipeUnit) : String = value.name

    @TypeConverters
    fun toRecipeUnit(value : String) : RecipeUnit = RecipeUnit.valueOf(value.toUpperCase())
}