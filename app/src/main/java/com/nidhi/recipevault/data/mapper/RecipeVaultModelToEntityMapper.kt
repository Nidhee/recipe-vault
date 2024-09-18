package com.nidhi.recipevault.com.nidhi.recipevault.data.mapper

import com.nidhi.recipevault.com.nidhi.recipevault.data.local.database.entity.MethodStepEntity
import com.nidhi.recipevault.com.nidhi.recipevault.data.local.model.MethodStepModel

/**
 *  Handles domain models to entity and entity to domain models mapping
 */
class RecipeVaultModelToEntityMapper {

    /**
     *  Maps method step model to method step entity
     */
    fun mapToMethodStepEntity(methodStepModel: MethodStepModel): MethodStepEntity {
        return MethodStepEntity(
            recipeId = methodStepModel.recipeId,
            stepOrder = methodStepModel.stepOrder,
            stepDescription = methodStepModel.stepDescription
        )
    }
    /**
     *  Maps method step entity to method step model
     */
    fun mapToMethodStepModel(methodStepEntity: MethodStepEntity): MethodStepModel {
        return MethodStepModel(
            recipeId = methodStepEntity.recipeId,
            stepOrder = methodStepEntity.stepOrder,
            stepDescription = methodStepEntity.stepDescription
        )
    }
}