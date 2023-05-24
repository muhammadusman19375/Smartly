package com.example.smartly.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.smartly.R
import com.example.smartly.base.BaseFragment
import com.example.smartly.databinding.FragmentHomeBinding
import com.example.smartly.utils.Category
import com.example.smartly.utils.gone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val difficultyList = arrayListOf("Easy", "Medium", "Hard")
    private val typeList = arrayListOf("Multiple", "Boolean")
    private val categoryList = arrayOf("Mythology", "Sports", "Geography", "History", "Politics", "Art", "Celebrities", "Animals", "Vehicles")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init() {
        binding.includedTopbar.ivBack.gone()
        setListeners()
        setSpinner()
    }

    private fun setListeners() {
        binding.btnStart.setOnClickListener {
            if (validateFields()) {
                val category = Category.get(binding.tvCategoryValue.text.toString())
                val difficulty = binding.tvDifficultyLevelValue.text.toString().lowercase()
                val type = binding.tvTypeValue.text.toString().lowercase()

                val action = HomeFragmentDirections.actionHomeFragmentToQuizFragment(category, difficulty, type)
                findNavController().navigate(action)
            }
        }

        binding.cvQuickMode.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToQuickModeFragment()
            findNavController().navigate(action)
        }

        binding.cvHistory.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToHistoryFragment()
            findNavController().navigate(action)
        }

    }

    private fun setSpinner() {
        val categoryAdapter = ArrayAdapter(requireContext(), R.layout.list_item_spinner_dropdown, categoryList)
        binding.tvCategoryValue.setAdapter(categoryAdapter)
        binding.tvCategoryValue.setDropDownBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_round_4_white1))

        binding.tvCategoryValue.setOnItemClickListener { parent, view, position, id ->
            binding.tvCategoryValue.error = null
        }

        val difficultyAdapter = ArrayAdapter(requireContext(), R.layout.list_item_spinner_dropdown, difficultyList)
        binding.tvDifficultyLevelValue.setAdapter(difficultyAdapter)
        binding.tvDifficultyLevelValue.setDropDownBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_round_4_white1))

        binding.tvDifficultyLevelValue.setOnItemClickListener { parent, view, position, id ->
            binding.tvDifficultyLevelValue.error = null
        }

        val typeAdapter = ArrayAdapter(requireContext(), R.layout.list_item_spinner_dropdown, typeList)
        binding.tvTypeValue.setAdapter(typeAdapter)
        binding.tvTypeValue.setDropDownBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_round_4_white1))

        binding.tvTypeValue.setOnItemClickListener { parent, view, position, id ->
            binding.tvTypeValue.error = null
        }
    }

    private fun validateFields(): Boolean {
        if (binding.tvCategoryValue.text.toString().isEmpty()) {
            binding.tvCategoryValue.error = "Select Category"
            return false
        }
        if (binding.tvDifficultyLevelValue.text.toString().isEmpty()) {
            binding.tvDifficultyLevelValue.error = "Select Difficulty Level"
            return false
        }
        if (binding.tvTypeValue.text.toString().isEmpty()) {
            binding.tvTypeValue.error = "Select Type"
            return false
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        setSpinner()
    }

}