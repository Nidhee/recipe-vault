package com.nidhi.recipevault.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.nidhi.recipevault.databinding.AddRecipeStep3Binding
import com.nidhi.recipevault.ui.viewmodel.AddRecipeViewModel

class AddRecipeStep3Fragment : Fragment() {

    private var _binding : AddRecipeStep3Binding? = null
    private val binding get() = _binding!!

    private val addRecipeViewModel: AddRecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddRecipeStep3Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}


