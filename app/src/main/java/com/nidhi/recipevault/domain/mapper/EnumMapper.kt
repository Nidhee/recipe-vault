package com.nidhi.recipevault.domain.mapper

import com.nidhi.recipevault.data.local.database.Cuisine
import com.nidhi.recipevault.data.local.database.Cuisine.*
import com.nidhi.recipevault.data.local.database.DifficultyLevel
import com.nidhi.recipevault.data.local.database.MealType
import com.nidhi.recipevault.data.local.database.MealType.*
import com.nidhi.recipevault.data.local.database.RecipeUnit
import com.nidhi.recipevault.domain.model.CuisineDomain
import com.nidhi.recipevault.domain.model.DifficultyLevelDomain
import com.nidhi.recipevault.domain.model.MealTypeDomain
import com.nidhi.recipevault.domain.model.RecipeUnitDomain
import javax.inject.Inject

class EnumMapper @Inject constructor(){
    /**
     *   DifficultyLevel Mappers : data to domain
      */
    fun mapDifficultyLevelToDomain(dataEnum: DifficultyLevel): DifficultyLevelDomain {
        return when (dataEnum) {
            DifficultyLevel.EASY -> DifficultyLevelDomain.EASY
            DifficultyLevel.MEDIUM -> DifficultyLevelDomain.MEDIUM
            DifficultyLevel.HARD -> DifficultyLevelDomain.HARD
        }
    }
    /**
     *   DifficultyLevel Mappers : domain to data
     */
    fun mapDifficultyLevelToData(domainEnum: DifficultyLevelDomain): DifficultyLevel {
        return when (domainEnum) {
            DifficultyLevelDomain.EASY -> DifficultyLevel.EASY
            DifficultyLevelDomain.MEDIUM -> DifficultyLevel.MEDIUM
            DifficultyLevelDomain.HARD -> DifficultyLevel.HARD
        }
    }

    /**
     * Cuisine Mappers : data to domain
      */
    fun mapCuisineToDomain(dataEnum: Cuisine): CuisineDomain {
        return when (dataEnum) {
            ITALIAN -> CuisineDomain.ITALIAN
            INDIAN -> CuisineDomain.INDIAN
            MEXICAN -> CuisineDomain.MEXICAN
            CHINESE -> CuisineDomain.CHINESE
            AMERICAN -> CuisineDomain.AMERICAN
            OTHER -> CuisineDomain.OTHER
        }
    }
    /**
     * Cuisine Mappers : domain to data
     */
    fun mapCuisineToData(domainEnum: CuisineDomain): Cuisine {
        return when (domainEnum) {
            CuisineDomain.ITALIAN -> ITALIAN
            CuisineDomain.INDIAN -> INDIAN
            CuisineDomain.MEXICAN -> MEXICAN
            CuisineDomain.CHINESE -> CHINESE
            CuisineDomain.AMERICAN -> AMERICAN
            CuisineDomain.OTHER -> OTHER
        }
    }

    /**
     * MealType Mappers : data to domain
     */
    fun mapMealTypeToDomain(dataEnum: MealType): MealTypeDomain {
        return when (dataEnum) {
            BREAKFAST -> MealTypeDomain.BREAKFAST
            LUNCH -> MealTypeDomain.LUNCH
            DINNER -> MealTypeDomain.DINNER
            SNACK -> MealTypeDomain.SNACK
            DRINK -> MealTypeDomain.DRINK
        }
    }
    /**
     * MealType Mappers : domain to data
     */
    fun mapMealTypeToData(domainEnum: MealTypeDomain): MealType {
        return when (domainEnum) {
            MealTypeDomain.BREAKFAST -> BREAKFAST
            MealTypeDomain.LUNCH -> LUNCH
            MealTypeDomain.DINNER -> DINNER
            MealTypeDomain.SNACK -> SNACK
            MealTypeDomain.DRINK -> DRINK
        }
    }

    /**
     * Recipe unit Mappers : data to domain
     */
    fun mapRecipeUnitToDomain(dataEnum: RecipeUnit): RecipeUnitDomain {
        return when (dataEnum) {
            RecipeUnit.NOS -> RecipeUnitDomain.NOS
            RecipeUnit.CUP -> RecipeUnitDomain.CUP
            RecipeUnit.TSP -> RecipeUnitDomain.TSP
            RecipeUnit.TBSP -> RecipeUnitDomain.TBSP
            RecipeUnit.GRAM -> RecipeUnitDomain.GRAM
            RecipeUnit.PINCH -> RecipeUnitDomain.PINCH
        }
    }
    /**
     * Recipe unit Mappers : domain to data
     */
    fun mapRecipeUnitToData(domainEnum: RecipeUnitDomain): RecipeUnit {
        return when (domainEnum) {
            RecipeUnitDomain.NOS -> RecipeUnit.NOS
            RecipeUnitDomain.CUP -> RecipeUnit.CUP
            RecipeUnitDomain.TSP -> RecipeUnit.TSP
            RecipeUnitDomain.TBSP -> RecipeUnit.TBSP
            RecipeUnitDomain.GRAM -> RecipeUnit.GRAM
            RecipeUnitDomain.PINCH -> RecipeUnit.PINCH
        }
    }
}