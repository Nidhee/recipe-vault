package com.nidhi.recipevault.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nidhi.recipevault.data.local.database.entity.MethodStepEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MethodStepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMethodSteps(steps : List<MethodStepEntity>)

    @Query("SELECT * FROM method_steps where recipe_id = :recipeId ORDER BY step_order ASC")
    fun getMethodStepsForRecipe(recipeId: Int) : Flow<List<MethodStepEntity>>
}