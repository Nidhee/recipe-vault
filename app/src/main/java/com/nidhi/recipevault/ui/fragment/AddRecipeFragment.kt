package com.nidhi.recipevault.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nidhi.recipevault.databinding.AddRecipeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [AddRecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddRecipeFragment : Fragment() {

    private var _binding : AddRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddRecipeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private fun initViews() {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of this fragment
         * @return A new instance of fragment AddRecipeFragment.
         */
        @JvmStatic
        fun newInstance() = AddRecipeFragment()
    }
}