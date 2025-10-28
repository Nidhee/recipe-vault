package com.nidhi.recipevault.ui.fragment

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.nidhi.recipevault.ui.adapter.AddRecipeStepsAdapter
import com.nidhi.recipevault.databinding.AddRecipeBinding
import com.nidhi.recipevault.ui.viewmodel.AddRecipeViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AddRecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddRecipeFragment : Fragment() {

    private var _binding : AddRecipeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddRecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddRecipeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // toolbar back button navigation handling
        binding.addRecipeToolBar.setNavigationOnClickListener {
            if (binding.stepsViewPager.currentItem > 0) {
                binding.stepsViewPager.currentItem = binding.stepsViewPager.currentItem - 1
                updateButtons()
            } else {
                viewModel.resetAll()
                parentFragmentManager.popBackStack()
            }
        }

        binding.stepsViewPager.adapter = AddRecipeStepsAdapter(this)
        binding.stepsViewPager.isUserInputEnabled = false
        // keep all steps in memory so previews persist when navigating
        binding.stepsViewPager.offscreenPageLimit = (binding.stepsViewPager.adapter?.itemCount ?: 1)

        binding.nextBtn.setOnClickListener {
            if (validateCurrentStep()) {
                binding.stepsViewPager.currentItem = binding.stepsViewPager.currentItem + 1
                viewModel.goToNextStep()
                updateButtons()
            }
        }
        binding.backBtn.setOnClickListener {
            if (binding.stepsViewPager.currentItem > 0) {
                binding.stepsViewPager.currentItem = binding.stepsViewPager.currentItem - 1
                viewModel.goToPreviousStep()
                updateButtons()
            }
        }
        binding.submitBtn.setOnClickListener {
            if (validateCurrentStep()) {
                viewModel.submit(
                    onSuccess = { parentFragmentManager.popBackStack() },
                    onError = { /* TODO show error */ }
                )
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.stepsViewPager.currentItem > 0) {
                    binding.stepsViewPager.currentItem = binding.stepsViewPager.currentItem - 1
                    viewModel.goToPreviousStep()
                    updateButtons()
                } else {
                    isEnabled = false
                    viewModel.resetAll()
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        })

        binding.stepsViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateButtons()
            }
        })

        updateButtons()
    }

    private fun updateButtons() {
        val currentIndex = binding.stepsViewPager.currentItem
        val last = (binding.stepsViewPager.adapter?.itemCount ?: 1) - 1
        binding.backBtn.visibility = if (currentIndex == 0) View.GONE else View.VISIBLE
        binding.nextBtn.visibility = if (currentIndex == last) View.GONE else View.VISIBLE
        binding.submitBtn.visibility = if (currentIndex == last) View.VISIBLE else View.GONE
    }

    /**
     *  pre-step validation
     */
    private fun validateCurrentStep(): Boolean {
        return when (binding.stepsViewPager.currentItem) {
            0 -> viewModel.validateStep1()
            1 -> viewModel.validateStep2()
            else -> true
        }
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