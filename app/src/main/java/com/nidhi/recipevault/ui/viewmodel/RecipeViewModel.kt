package com.nidhi.recipevault.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nidhi.recipevault.domain.model.InitStatus
import com.nidhi.recipevault.domain.usecase.ObserveDatabaseInitStatusUseCase
import com.nidhi.recipevault.domain.usecase.GetAllRecipesUseCase
import com.nidhi.recipevault.ui.state.RecipeUiState
import com.nidhi.recipevault.utils.LogUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val getAllRecipesUseCase: GetAllRecipesUseCase,
                                          private val observeDbInitStatus: ObserveDatabaseInitStatusUseCase) : ViewModel() {

    private val _recipesState = MutableStateFlow<RecipeUiState>(RecipeUiState.Loading)
    val recipesState: StateFlow<RecipeUiState> = _recipesState.asStateFlow()

    init {
        Log.d(LogUtils.getTag(this::class.java), "Inside init")
        checkDatabaseInitializationError()
        getAllRecipes()
    }
    private fun checkDatabaseInitializationError() {
        viewModelScope.launch {
            observeDbInitStatus().collect { status ->
                if (status is InitStatus.Error) {
                    _recipesState.value = RecipeUiState.Error("Database initialization error: ${status.throwable}")
                }
            }
        }
    }
    private fun getAllRecipes() {
        Log.d(LogUtils.getTag(this::class.java), "Fetching recipes")
        viewModelScope.launch {
            getAllRecipesUseCase.invoke().onStart {
                Log.d(LogUtils.getTag(this::class.java), "State: Loading")
                _recipesState.value = RecipeUiState.Loading
            }.catch { exception ->
                Log.e(LogUtils.getTag(this::class.java), "Error fetching recipes", exception)
                _recipesState.value = RecipeUiState.Error(exception.message ?: "Unknown Error")
            }.collect { recipes ->
                Log.d(LogUtils.getTag(this::class.java), "Fetched recipes: ${recipes.size}")
                if (recipes.isEmpty()) {
                    Log.d(LogUtils.getTag(this::class.java), "State: NoRecipes")
                    _recipesState.value = RecipeUiState.NoRecipes
                } else {
                    Log.d(LogUtils.getTag(this::class.java), "State: Success with recipes")
                    _recipesState.value = RecipeUiState.Success(recipes)
                }
            }
        }
    }

}