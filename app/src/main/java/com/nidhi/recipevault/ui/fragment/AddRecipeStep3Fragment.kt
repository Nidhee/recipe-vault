package com.nidhi.recipevault.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nidhi.recipevault.com.nidhi.recipevault.ui.adapter.AddRecipeStep3IngredientItemAdapter
import com.nidhi.recipevault.databinding.AddRecipeStep3Binding
import com.nidhi.recipevault.ui.viewmodel.AddRecipeViewModel
import kotlinx.coroutines.launch

class AddRecipeStep3Fragment : Fragment() {

    private var _binding : AddRecipeStep3Binding? = null
    private val binding get() = _binding!!
    private val addRecipeViewModel: AddRecipeViewModel by activityViewModels()
    private lateinit var ingredientsAdapter: AddRecipeStep3IngredientItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddRecipeStep3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupIngredientsRecyclerView()
        observeIngredients()
        setupAddIngredientButton()
        setupErrorMessage()
    }

    private fun setupIngredientsRecyclerView() {
        ingredientsAdapter = AddRecipeStep3IngredientItemAdapter(
            onItemRemove = { index ->
                addRecipeViewModel.removeIngredient(index)
            },
            onUnitSelected = { index, unit ->
                addRecipeViewModel.updateIngredientUnit(index, unit)
            },
            onItemNameChanged = { index, name ->
                addRecipeViewModel.updateIngredientItem(index, name)
            },
            onQuantityChanged = { index, qty ->
                addRecipeViewModel.updateIngredientQuantity(index, qty)
            }
        )

        binding.addRecipeIngredientsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ingredientsAdapter
        }
    }

    private fun observeIngredients() {
        lifecycleScope.launch {
            addRecipeViewModel.step3Fields.collect { step3Fields ->
                ingredientsAdapter.submitList(step3Fields.ingredients.toList())
            }
        }
    }

    private fun setupAddIngredientButton() {
        binding.addRecipeAddIngredientBtn.setOnClickListener {
            addRecipeViewModel.addIngredient()
        }
    }
    private fun setupErrorMessage(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                addRecipeViewModel.step3Errors.collect { errors ->
                    if (errors.ingredientError != null) {
                        binding.addRecipeIngredientsError.text = errors.ingredientError
                        binding.addRecipeIngredientsError.visibility = View.VISIBLE
                    } else {
                        binding.addRecipeIngredientsError.text = ""
                        binding.addRecipeIngredientsError.visibility = View.GONE
                    }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


