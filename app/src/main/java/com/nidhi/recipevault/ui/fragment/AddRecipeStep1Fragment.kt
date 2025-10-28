package com.nidhi.recipevault.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nidhi.recipevault.databinding.AddRecipeStep1Binding
import com.nidhi.recipevault.ui.viewmodel.AddRecipeViewModel
import kotlinx.coroutines.launch

class AddRecipeStep1Fragment : Fragment() {

    private var _binding : AddRecipeStep1Binding? = null
    private val binding get() = _binding!!

    private val addRecipeViewModel: AddRecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddRecipeStep1Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextInputs()
        observeValidation()
    }

    private fun setupTextInputs() {
        binding.addRecipeName.doOnTextChanged { text, _, _, _ ->
            addRecipeViewModel.setName(text?.toString()?.trim().orEmpty())
        }
        binding.addRecipeCookTime.doOnTextChanged { text, _, _, _ ->
            val filtered = text?.toString()?.filter { it.isDigit() }.orEmpty()
            if (filtered != text?.toString()) {
                // replace non-digits
                binding.addRecipeCookTime.setText(filtered)
                binding.addRecipeCookTime.setSelection(filtered.length)
            }
            addRecipeViewModel.setCookTimeMinutes(filtered)
        }
        binding.addRecipePrepTime.doOnTextChanged { text, _, _, _ ->
            val filtered = text?.toString()?.filter { it.isDigit() }.orEmpty()
            if (filtered != text?.toString()) {
                binding.addRecipePrepTime.setText(filtered)
                binding.addRecipePrepTime.setSelection(filtered.length)
            }
            addRecipeViewModel.setPrepTimeMinutes(filtered)
        }
    }

    private fun observeValidation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    addRecipeViewModel.step1Errors.collect { errors ->
                        binding.addRecipeNameLayout.error = errors.nameError
                        binding.addRecipeCookTimeLayout.error = errors.cookTimeError
                        binding.addRecipePrepTimeLayout.error = errors.prepTimeError
                    }
                }
            }
        }
    }
}


