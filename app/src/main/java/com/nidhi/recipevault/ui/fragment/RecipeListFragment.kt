package com.nidhi.recipevault.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nidhi.recipevault.R
import com.nidhi.recipevault.ui.adapter.RecipeAdapter
import com.nidhi.recipevault.ui.viewmodel.RecipeViewModel
import com.nidhi.recipevault.utils.LogUtils
import com.nidhi.recipevault.databinding.RecipeListBinding
import com.nidhi.recipevault.domain.model.Recipe
import com.nidhi.recipevault.ui.fragment.AddRecipeFragment
import com.nidhi.recipevault.ui.state.RecipeUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private var _binding: RecipeListBinding? = null
    private val binding get() = _binding!!
    private val recipeViewModel: RecipeViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipeListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(LogUtils.getTag(this::class.java), "RecipeListFragment class onViewCreated called")
        _binding = RecipeListBinding.bind(view)

        setupRecyclerView()
        setupAddRecipeFabScrollBehavior()
        setupAddRecipeFabClick()

        // Collect StateFlow in lifecycle-aware manner
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                recipeViewModel.recipesState.flowWithLifecycle(lifecycle).collectLatest { uiState ->
                    when (uiState) {
                        is RecipeUiState.Loading -> showLoading()
                        is RecipeUiState.Success -> showRecipes(uiState.recipes)
                        is RecipeUiState.Error -> showError(uiState.message)
                        is RecipeUiState.NoRecipes -> showNoData()
                    }
                }
            }
        }
    }
    private fun setupRecyclerView() {
        binding.recipeList.apply {
            layoutManager = GridLayoutManager(context, 2)
            recipeAdapter = RecipeAdapter { recipe: Recipe ->
                onRecipeClicked(recipe)
            }
            adapter = recipeAdapter
        }

    }

    private fun onRecipeClicked(recipe: Recipe) {
        openRecipeDetailFragment(recipe)
    }
    private fun openRecipeDetailFragment(item: Recipe) {
        val detailFragment =  RecipeDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable("recipe", item)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(LogUtils.getTag(this::class.java), "RecipeListFragment class onDestroyView called")
        _binding = null
    }

    private fun showLoading() {
        binding.loadingScreen.visibility = View.VISIBLE
        binding.errorScreen.visibility = View.GONE
        binding.noDataScreen.visibility = View.GONE
        binding.recipeList.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.errorScreen.text = message
        binding.errorScreen.visibility = View.VISIBLE
        binding.loadingScreen.visibility = View.GONE
        binding.noDataScreen.visibility = View.GONE
        binding.recipeList.visibility = View.GONE
    }

    private fun showNoData() {
        binding.noDataScreen.visibility = View.VISIBLE
        binding.errorScreen.visibility = View.GONE
        binding.loadingScreen.visibility = View.GONE
        binding.recipeList.visibility = View.GONE
    }

    private fun showRecipes(recipes: List<Recipe>) {
        binding.loadingScreen.visibility = View.GONE
        binding.errorScreen.visibility = View.GONE
        binding.noDataScreen.visibility = View.GONE
        binding.recipeList.visibility = View.VISIBLE
        recipeAdapter.setRecipes(recipes)
    }

    private fun setupAddRecipeFabScrollBehavior(){
        binding.recipeList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    // Scrolling down → shrink
                    if (binding.addRecipeFab.isExtended) binding.addRecipeFab.shrink()
                } else if (dy < 0) {
                    // Scrolling up → extend
                    if (!binding.addRecipeFab.isExtended) binding.addRecipeFab.extend()
                }
            }
        })
    }

    private fun setupAddRecipeFabClick() {
        binding.addRecipeFab.setOnClickListener {
            Log.d(LogUtils.getTag(this::class.java), "RecipeListFragment class Add recipe FAB clicked!")
            openAddRecipeFragment()
        }
    }

    private fun openAddRecipeFragment() {
        val addRecipeFragment =  AddRecipeFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, addRecipeFragment)
            .addToBackStack(null)
            .commit()
    }
}