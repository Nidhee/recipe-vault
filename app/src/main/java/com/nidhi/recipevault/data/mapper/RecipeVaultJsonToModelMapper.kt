package com.nidhi.recipevault.data.mapper

import android.util.Log
import com.google.gson.Gson
import com.nidhi.recipevault.data.local.model.MethodStepModel
import com.nidhi.recipevault.utils.LogUtils
import com.nidhi.recipevault.data.local.model.RecipeVaultModel
import javax.inject.Inject

/**
 * Handles json to domain models mapping
 */
class RecipeVaultJsonToModelMapper @Inject constructor(){

    /**
     * Parses json to domain model classes
     */
    suspend fun parseJsonToDomainModel(jsonString: String): RecipeVaultModel? {
        val gson = Gson()
        val recipeVault = gson.fromJson(jsonString, RecipeVaultModel::class.java)
        Log.d(LogUtils.getTag(this::class.java), "recipe vault object $recipeVault")
        return recipeVault
    }
    /**
     * TODO : check if we need this method
     * Maps json method string to Method Step model classs
     */
    fun mapJsonMethodStepsToDomainModel(recipeId: Int, methodSteps: List<String>): List<MethodStepModel> {
        return methodSteps.mapIndexed { index, stepDescription ->
            MethodStepModel(
                recipeId = recipeId,
                stepOrder = index + 1,
                stepDescription = stepDescription
            )
        }
    }
}