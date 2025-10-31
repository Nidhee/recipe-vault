package com.nidhi.recipevault.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nidhi.recipevault.ui.fragment.IngredientsFragment
import com.nidhi.recipevault.ui.fragment.MethodFragment
import com.nidhi.recipevault.domain.model.Recipe

class RecipePagerAdapter (fragment: Fragment, private val recipe: Recipe) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2 // Two tabs: Ingredients & Method

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IngredientsFragment.newInstance(recipe)
            1 -> MethodFragment.newInstance(recipe)
            else -> throw IllegalStateException("Invalid tab position")
        }
    }
}