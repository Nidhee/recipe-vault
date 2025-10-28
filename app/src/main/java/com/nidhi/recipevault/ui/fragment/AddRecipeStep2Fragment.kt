package com.nidhi.recipevault.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.nidhi.recipevault.R
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.CuisineDomain
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.DifficultyLevelDomain
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.MealTypeDomain
import com.nidhi.recipevault.databinding.AddRecipeStep2Binding
import com.nidhi.recipevault.ui.viewmodel.AddRecipeViewModel
import kotlinx.coroutines.launch
import com.nidhi.recipevault.com.nidhi.recipevault.utils.capitalize

class AddRecipeStep2Fragment : Fragment() {

    private var _binding : AddRecipeStep2Binding? = null
    private val binding get() = _binding!!

    private val addRecipeViewModel: AddRecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddRecipeStep2Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextInputs()
        setupDropdowns()
        observeValidation()
    }
    private fun setupTextInputs() {
        // Servings input - only allow digits
        binding.addRecipeServings.doOnTextChanged { text, _, _, _ ->
            val filtered = text?.toString()?.filter { it.isDigit() }.orEmpty()
            if (filtered != text?.toString()) {
                binding.addRecipeServings.setText(filtered)
                binding.addRecipeServings.setSelection(filtered.length)
            }
            addRecipeViewModel.setServings(filtered)
        }
    }

    private fun setupDropdowns() {
        binding.addRecipeDifficulty.setOnClickListener {
            showDifficultyDialog(binding.addRecipeDifficulty)
        }
        binding.addRecipeMealType.setOnClickListener {
            showMealTypeDialog(binding.addRecipeMealType)
        }
        binding.addRecipeCuisine.setOnClickListener {
            showCuisineDialog(binding.addRecipeCuisine)
        }
    }

    private fun showDifficultyDialog(editText: TextInputEditText) {
        val options = DifficultyLevelDomain.entries.map { it.name.capitalize() }.toTypedArray()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.select_difficulty_level)
            .setItems(options) { _, which ->
                val selected = DifficultyLevelDomain.entries[which]
                editText.setText(selected.name.capitalize())
                addRecipeViewModel.setDifficultyLevel(selected)
            }
            .show()
    }

    private fun showMealTypeDialog(editText: TextInputEditText) {
        val options = MealTypeDomain.entries.map { it.name.capitalize() }.toTypedArray()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.select_meal_type)
            .setItems(options) { _, which ->
                val selected = MealTypeDomain.entries[which]
                editText.setText(selected.name.capitalize())
                addRecipeViewModel.setMealType(selected)
            }
            .show()
    }

    private fun showCuisineDialog(editText: TextInputEditText) {
        val options = CuisineDomain.entries.map { it.name.capitalize() }.toTypedArray()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.select_cuisine)
            .setItems(options) { _, which ->
                val selected = CuisineDomain.entries[which]
                editText.setText(selected.name.capitalize())
                addRecipeViewModel.setCuisine(selected)
            }
            .show()
    }

    private fun observeValidation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    addRecipeViewModel.step2Errors.collect { errors ->
                        binding.addRecipeServingsLayout.error = errors.servingsError
                        binding.addRecipeDifficultyLayout.error = errors.difficultyError
                        binding.addRecipeMealTypeLayout.error = errors.mealTypeError
                        binding.addRecipeCuisineLayout.error = errors.cuisineError
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


