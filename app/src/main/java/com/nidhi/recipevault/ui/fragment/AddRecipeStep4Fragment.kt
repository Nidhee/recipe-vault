package com.nidhi.recipevault.com.nidhi.recipevault.ui.fragment

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
import com.nidhi.recipevault.databinding.AddRecipeStep4Binding
import com.nidhi.recipevault.ui.adapter.AddRecipeStep4MethodItemAdapter
import com.nidhi.recipevault.ui.viewmodel.AddRecipeViewModel
import kotlinx.coroutines.launch

class AddRecipeStep4Fragment : Fragment() {

    private var _binding : AddRecipeStep4Binding? = null
    private val binding get() = _binding!!

    private val addRecipeViewModel: AddRecipeViewModel by activityViewModels()
    private lateinit var methodStepsAdapter: AddRecipeStep4MethodItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddRecipeStep4Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMethodStepsRecyclerView()
        observeMethodSteps()
        setupAddStepButton()
        setupMethodStepsErrorMessage()
    }
    private fun setupMethodStepsRecyclerView() {
        methodStepsAdapter = AddRecipeStep4MethodItemAdapter(
            onItemRemove = { index -> removeMethodStep(index) },
            onDescriptionChanged = { index, desc -> updateMethodStepDescription(index, desc) }
        )

        binding.addMethodStepsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = methodStepsAdapter
        }
    }

    private fun setupAddStepButton() {
        binding.addMethodStepBtn.setOnClickListener {
            addMethodStep()
        }
    }

    private fun observeMethodSteps() {
        lifecycleScope.launchWhenStarted {
            addRecipeViewModel.step4Fields.collect { fields ->
                methodStepsAdapter.submitList(fields.methodSteps.toList())
            }
        }
    }
    private fun setupMethodStepsErrorMessage() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                addRecipeViewModel.step4Errors.collect { errors ->
                    if (errors.methodStepError != null) {
                        binding.addMethodStepsError.text = errors.methodStepError
                        binding.addMethodStepsError.visibility = View.VISIBLE
                    } else {
                        binding.addMethodStepsError.text = ""
                        binding.addMethodStepsError.visibility = View.GONE
                    }
                }
            }
        }
    }
    private fun addMethodStep() {
        addRecipeViewModel.addMethodStep()
    }

    private fun removeMethodStep(index: Int) {
        addRecipeViewModel.removeMethodStep(index)
    }

    private fun updateMethodStepDescription(index: Int, description: String) {
        addRecipeViewModel.updateMethodStepDescription(index, description)
    }
}


