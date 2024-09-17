package com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.Cuisine
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.DifficultyLevel
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.MealType

@Entity(tableName = "recipes")
data class RecipeVaultEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "recipe_id") val recipeId: Int,
    val name: String,
    val description: String?,
    @ColumnInfo(name = "prep_time_minutes") val prepTimeMinutes: Int?,
    @ColumnInfo(name = "cook_time_minutes") val cookTimeMinutes: Int?,
    val servings: Int?,
    @ColumnInfo(name = "difficulty_level") val difficultyLevel: DifficultyLevel?,
    @ColumnInfo(name = "meal_type") val mealType: MealType?,
    val cuisine: Cuisine?,
    val thumbnail: String?
)