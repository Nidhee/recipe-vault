package com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "method_steps",
    foreignKeys = [ForeignKey(
        entity = RecipeVaultEntity::class,
        parentColumns = ["recipe_id"],
        childColumns = ["recipe_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MethodStepEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "step_id") val stepId: Int = 0,
    @ColumnInfo(name = "recipe_id") val recipeId: Int,
    @ColumnInfo(name = "step_order") val stepOrder: Int,
    @ColumnInfo(name = "step_description") val stepDescription: String
)