package com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.RecipeUnit


@Entity(
    tableName = "ingredients",
    foreignKeys = [ForeignKey(
        entity = RecipeVaultEntity::class,
        parentColumns = ["recipe_id"],
        childColumns = ["recipe_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ingredient_id") val ingredientId: Int = 0,
    @ColumnInfo(name = "recipe_id") val recipeId: Int,
    val item: String,
    val qty: Double?,
    @ColumnInfo(name = "max_qty") val maxQty: Double?,
    val unit: RecipeUnit?
)