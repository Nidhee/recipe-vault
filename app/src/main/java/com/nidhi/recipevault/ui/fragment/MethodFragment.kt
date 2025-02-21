package com.nidhi.recipevault.com.nidhi.recipevault.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nidhi.recipevault.com.nidhi.recipevault.domain.model.MethodStep
import com.nidhi.recipevault.databinding.RecipeDetailMethodstepBinding
import com.nidhi.recipevault.domain.model.Recipe

class MethodFragment : Fragment() {

    private var recipe: Recipe? = null
    private var _binding : RecipeDetailMethodstepBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipeDetailMethodstepBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = RecipeDetailMethodstepBinding.bind(view)
        arguments?.let {
            recipe = it.getParcelable(ARG_RECIPE) // Retrieve recipe data
        }
        initViews()
    }
    private fun initViews() {
        recipe?.methodSteps?.let { setMethodStepData(it) }
        binding.recipeDetailMethodStepTextView.post {
            binding.recipeDetailMethodStepTextView.requestLayout()
        }
    }
    private fun setMethodStepData(methodSteps: List<MethodStep>) {
        val methodStepText =
            methodSteps.mapIndexed { index, methodStep -> "${index + 1}. ${methodStep.stepDescription}" }
                .joinToString("\n\n")
        binding.recipeDetailMethodStepTextView.text = methodStepText
    }
    companion object {
        private const val ARG_RECIPE = "recipe"
        fun newInstance(recipe: Recipe) = MethodFragment().apply{
            arguments = Bundle().apply {
                putParcelable(ARG_RECIPE, recipe)
            }
        }
    }
}