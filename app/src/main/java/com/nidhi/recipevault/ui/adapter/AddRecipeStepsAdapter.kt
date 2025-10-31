package com.nidhi.recipevault.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nidhi.recipevault.ui.fragment.AddRecipeStep4Fragment
import com.nidhi.recipevault.ui.fragment.AddRecipeStep1Fragment
import com.nidhi.recipevault.ui.fragment.AddRecipeStep2Fragment
import com.nidhi.recipevault.ui.fragment.AddRecipeStep3Fragment

class AddRecipeStepsAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> AddRecipeStep1Fragment()
        1 -> AddRecipeStep2Fragment()
        2 -> AddRecipeStep3Fragment()
        else -> AddRecipeStep4Fragment()
    }
}


